package ua.dp.michaellang.gitsurf.services;

import com.google.gson.reflect.TypeToken;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.StargazerService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.eclipse.egit.github.core.client.IGitHubConstants.*;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_FIRST;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_SIZE;

/**
 * Date: 13.04.17
 *
 * @author Michael Lang
 */
public class GitSurfStargazerService extends StargazerService {
    public static final String SEGMENT_WATCHERS = "/subscribers";
    public static final String SEGMENT_SUBSCRIPTIONS = "/subscriptions";
    public static final String SEGMENT_SUBSCRIPTION = "/subscription";

    public static final String PARAM_SUBSCRIBED = "subscribed";
    public static final String PARAM_IGNORED = "ignored";

    public GitSurfStargazerService() {
        super();
    }

    public GitSurfStargazerService(GitHubClient client) {
        super(client);
    }

    /* List watchers */

    public PageIterator<User> pageWatchers(IRepositoryIdProvider repository) {
        return pageWatchers(repository, PAGE_SIZE);
    }

    public PageIterator<User> pageWatchers(IRepositoryIdProvider repository,
            int size) {
        return pageWatchers(repository, PAGE_FIRST, size);
    }

    public PageIterator<User> pageWatchers(IRepositoryIdProvider repository,
            int start, int size) {
        PagedRequest<User> request = createWatchersRequest(repository, start, size);
        return createPageIterator(request);
    }

    protected PagedRequest<User> createWatchersRequest(
            IRepositoryIdProvider repository, int start, int size) {
        String id = getId(repository);
        PagedRequest<User> request = createPagedRequest(start, size);
        StringBuilder uri = new StringBuilder(SEGMENT_REPOS)
                .append('/').append(id)
                .append(SEGMENT_WATCHERS);
        request.setUri(uri);
        request.setType(new TypeToken<List<User>>() {
        }.getType());
        return request;
    }

    /* List repositories being watched */

    /**
     * Create page watched request
     *
     * @param user
     * @param start
     * @param size
     * @return request
     */
    protected PagedRequest<Repository> createWatchedRequest(String user,
            int start, int size) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null"); //$NON-NLS-1$
        if (user.length() == 0)
            throw new IllegalArgumentException("User cannot be empty"); //$NON-NLS-1$

        PagedRequest<Repository> request = createPagedRequest(start, size);
        StringBuilder uri = new StringBuilder(SEGMENT_USERS)
                .append('/').append(user)
                .append(SEGMENT_SUBSCRIPTIONS);
        request.setUri(uri);
        request.setType(new TypeToken<List<Repository>>() {
        }.getType());
        return request;
    }

    /**
     * Create page watched request
     *
     * @param start
     * @param size
     * @return request
     */
    protected PagedRequest<Repository> createWatchedRequest(int start, int size) {
        PagedRequest<Repository> request = createPagedRequest(start, size);
        request.setUri(SEGMENT_USER + SEGMENT_SUBSCRIPTIONS);
        request.setType(new TypeToken<List<Repository>>() {
        }.getType());
        return request;
    }

    /**
     * Get repositories watched by the given user
     *
     * @param user
     * @return non-null but possibly empty list of repositories
     * @throws IOException
     */
    public List<Repository> getWatched(String user) throws IOException {
        PagedRequest<Repository> request = createWatchedRequest(user,
                PAGE_FIRST, PAGE_SIZE);
        return getAll(request);
    }

    /**
     * Page repositories being watched by given user
     *
     * @param user
     * @return page iterator
     * @throws IOException
     */
    public PageIterator<Repository> pageWatched(String user) throws IOException {
        return pageWatched(user, PAGE_SIZE);
    }

    /**
     * Page repositories being watched by given user
     *
     * @param user
     * @param size
     * @return page iterator
     * @throws IOException
     */
    public PageIterator<Repository> pageWatched(String user, int size)
            throws IOException {
        return pageWatched(user, PAGE_FIRST, size);
    }

    /**
     * Page repositories being watched by given user
     *
     * @param user
     * @param start
     * @param size
     * @return page iterator
     * @throws IOException
     */
    public PageIterator<Repository> pageWatched(String user, int start, int size)
            throws IOException {
        PagedRequest<Repository> request = createWatchedRequest(user, start,
                size);
        return createPageIterator(request);
    }

    /**
     * Get repositories watched by the currently authenticated user
     *
     * @return non-null but possibly empty list of repositories
     * @throws IOException
     */
    public List<Repository> getWatched() throws IOException {
        PagedRequest<Repository> request = createWatchedRequest(PAGE_FIRST,
                PAGE_SIZE);
        return getAll(request);
    }

    /**
     * Page repositories being watched by the currently authenticated user
     *
     * @return page iterator
     * @throws IOException
     */
    public PageIterator<Repository> pageWatched() throws IOException {
        return pageWatched(PAGE_SIZE);
    }

    /**
     * Page repositories being watched by the currently authenticated user
     *
     * @param size
     * @return page iterator
     * @throws IOException
     */
    public PageIterator<Repository> pageWatched(int size) throws IOException {
        return pageWatched(PAGE_FIRST, size);
    }

    /**
     * Page repositories being watched by the currently authenticated user
     *
     * @param start
     * @param size
     * @return page iterator
     * @throws IOException
     */
    public PageIterator<Repository> pageWatched(int start, int size)
            throws IOException {
        PagedRequest<Repository> request = createWatchedRequest(start, size);
        return createPageIterator(request);
    }

    /* Repository Subscription */

    /**
     * Get a Repository Subscription
     *
     * @param repository
     * @return true if watch, false otherwise
     * @throws IOException
     */
    public boolean isWatching(IRepositoryIdProvider repository)
            throws IOException {
        String id = getId(repository);
        String uri = SEGMENT_REPOS + '/' + id + SEGMENT_SUBSCRIPTION;
        return check(uri);
    }

    /**
     * Set a Repository Subscription
     *
     * @param repository
     * @throws IOException
     */
    public void watch(IRepositoryIdProvider repository,
            boolean receiveNotifications) throws IOException {
        String id = getId(repository);
        String uri = SEGMENT_REPOS + '/' + id + SEGMENT_SUBSCRIPTION;
        Map<String, Boolean> map;
        map = receiveNotifications
                ? Collections.singletonMap(PARAM_SUBSCRIBED, true)
                : Collections.singletonMap(PARAM_IGNORED, true);

        client.put(uri, map, null);
    }

    /**
     * Delete a Repository Subscription
     *
     * @param repository
     * @throws IOException
     */
    public void unwatch(IRepositoryIdProvider repository) throws IOException {
        String id = getId(repository);
        String uri = SEGMENT_REPOS + '/' + id + SEGMENT_SUBSCRIPTION;
        client.delete(uri);
    }

}

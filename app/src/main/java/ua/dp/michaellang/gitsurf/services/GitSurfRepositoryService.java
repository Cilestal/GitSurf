package ua.dp.michaellang.gitsurf.services;

import com.google.gson.reflect.TypeToken;
import org.eclipse.egit.github.core.IRepositoryIdProvider;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PageIterator;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_FORKS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_REPOS;
import static org.eclipse.egit.github.core.client.IGitHubConstants.SEGMENT_USERS;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_FIRST;
import static org.eclipse.egit.github.core.client.PagedRequest.PAGE_SIZE;

/**
 * Date: 13.04.17
 *
 * @author Michael Lang
 */
public class GitSurfRepositoryService extends RepositoryService {
    public static final String SEGMENT_COLLABORATORS = "/collaborators";
    public static final String SEGMENT_CONTRIBUTORS = "/contributors";

    public GitSurfRepositoryService() {
    }

    public GitSurfRepositoryService(GitHubClient client) {
        super(client);
    }

    public PageIterator<Repository> pageRepositories(String user,
            Map<String, String> filterData, int start, int size) {
        if (user == null)
            throw new IllegalArgumentException("User cannot be null"); //$NON-NLS-1$
        if (user.length() == 0)
            throw new IllegalArgumentException("User cannot be empty"); //$NON-NLS-1$

        StringBuilder uri = new StringBuilder(SEGMENT_USERS);
        uri.append('/').append(user);
        uri.append(SEGMENT_REPOS);
        PagedRequest<Repository> request = createPagedRequest(start, size);
        request.setUri(uri);
        request.setParams(filterData);
        request.setType(new TypeToken<List<Repository>>() {
        }.getType());

        return createPageIterator(request);
    }

    public PageIterator<Repository> pageRepositories(String user,
            Map<String, String> filterData, int size) {
        return pageRepositories(user, filterData, PAGE_FIRST, size);
    }

    public List<User> getCollaborators(IRepositoryIdProvider repository)
            throws IOException {
        String id = getId(repository);
        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(id);
        uri.append(SEGMENT_COLLABORATORS);
        PagedRequest<User> request = createPagedRequest();
        request.setUri(uri);
        request.setType(new TypeToken<List<User>>() {
        }.getType());
        return getAll(request);
    }

    private String createUpdateUri(IRepositoryIdProvider repository,
            String user) {
        String id = getId(repository);
        if (user == null)
            throw new IllegalArgumentException("User cannot be null"); //$NON-NLS-1$
        if (user.length() == 0)
            throw new IllegalArgumentException("User cannot be empty"); //$NON-NLS-1$

        return SEGMENT_REPOS + '/' + id + SEGMENT_COLLABORATORS + '/' + user;
    }

    public boolean isCollaborator(IRepositoryIdProvider repository, String user)
            throws IOException {
        return check(createUpdateUri(repository, user));
    }

    public void addCollaborator(IRepositoryIdProvider repository, String user)
            throws IOException {
        client.put(createUpdateUri(repository, user));
    }

    public void removeCollaborator(IRepositoryIdProvider repository, String user)
            throws IOException {
        client.delete(createUpdateUri(repository, user));
    }

    public PageIterator<User> pageCollaborators(IRepositoryIdProvider repository) {
        return pageCollaborators(repository, PAGE_SIZE);
    }

    public PageIterator<User> pageCollaborators(IRepositoryIdProvider repository,
            int size) {
        return pageCollaborators(repository, PAGE_FIRST, size);
    }

    public PageIterator<User> pageCollaborators(IRepositoryIdProvider repository,
            int start, int size) {
        PagedRequest<User> request = createCollaboratorsRequest(repository, start, size);
        return createPageIterator(request);
    }

    protected PagedRequest<User> createCollaboratorsRequest(
            IRepositoryIdProvider repository, int start, int size) {
        String id = getId(repository);
        PagedRequest<User> request = createPagedRequest(start, size);
        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(id);
        uri.append(SEGMENT_COLLABORATORS);
        request.setUri(uri);
        request.setType(new TypeToken<List<User>>() {
        }.getType());
        return request;
    }

    public PageIterator<User> pageContributors(IRepositoryIdProvider repository) {
        return pageContributors(repository, PAGE_SIZE);
    }

    public PageIterator<User> pageContributors(IRepositoryIdProvider repository,
            int size) {
        return pageContributors(repository, PAGE_FIRST, size);
    }

    public PageIterator<User> pageContributors(IRepositoryIdProvider repository,
            int start, int size) {
        PagedRequest<User> request = createContributorsRequest(repository, start, size);
        return createPageIterator(request);
    }

    protected PagedRequest<User> createContributorsRequest(
            IRepositoryIdProvider repository, int start, int size) {
        String id = getId(repository);
        PagedRequest<User> request = createPagedRequest(start, size);
        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(id);
        uri.append(SEGMENT_CONTRIBUTORS);
        request.setUri(uri);
        request.setType(new TypeToken<List<User>>() {
        }.getType());
        return request;
    }

    public PageIterator<Repository> pageForks(IRepositoryIdProvider repository,
            Map<String, String> filterData) {
        return pageForks(repository, filterData, PAGE_SIZE);
    }

    public PageIterator<Repository> pageForks(IRepositoryIdProvider repository,
            Map<String, String> filterData, int size) {
        return pageForks(repository, filterData, PAGE_FIRST, size);
    }

    public PageIterator<Repository> pageForks(IRepositoryIdProvider repository,
            Map<String, String> filterData, int start, int size) {
        PagedRequest<Repository> request = createPagedForkRequest(repository,
                filterData, start, size);
        return createPageIterator(request);
    }

    protected PagedRequest<Repository> createPagedForkRequest(
            IRepositoryIdProvider repository, Map<String, String> filterData,
            int start, int size) {
        final String id = getId(repository);
        StringBuilder uri = new StringBuilder(SEGMENT_REPOS);
        uri.append('/').append(id);
        uri.append(SEGMENT_FORKS);
        PagedRequest<Repository> request = createPagedRequest(start, size);
        request.setUri(uri);
        request.setParams(filterData);
        request.setType(new TypeToken<List<Repository>>() {
        }.getType());
        return request;
    }


}

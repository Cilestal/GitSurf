package ua.dp.michaellang.gitsurf.services;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.UserService;
import ua.dp.michaellang.gitsurf.services.entity.SearchUser;
import ua.dp.michaellang.gitsurf.services.entity.SearchUserList;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Date: 13.04.17
 *
 * @author Michael Lang
 */
public class GitSurfUserService extends UserService {
    public GitSurfUserService() {
    }

    public GitSurfUserService(GitHubClient client) {
        super(client);
    }

    private PagedRequest<SearchUser> createSearchRequest(String user) {
        PagedRequest<SearchUser> request = createPagedRequest();

        StringBuilder uri = new StringBuilder("/legacy/user/search/");
        String encodedQuery;
        try {
            encodedQuery = URLEncoder.encode(user, "UTF-8").replace("+", "%20").replace(".", "%2E");
        } catch (UnsupportedEncodingException e) {
            encodedQuery = user.replace("+", "%20").replace(".", "%2E");
        }
        uri.append(encodedQuery);

        request.setUri(uri);
        request.setType(SearchUserList.class);

        return request;
    }

    public List<SearchUser> searchUsers(String login) throws IOException {
        if (login == null) {
            throw new IllegalArgumentException("User cannot be null");
        } else if (login.length() == 0) {
            throw new IllegalArgumentException("User cannot be empty");
        } else {
            PagedRequest<SearchUser> request = this.createSearchRequest(login);
            return getAll(createPageIterator(request));
        }
    }
}

package ua.dp.michaellang.gitsurf;

/**
 * Date: 21.02.17
 *
 * @author Michael Lang
 */
public interface Constants {
    int PAGE_ITERATOR_SIZE = 50;

    interface Prefs {
        String PREF_NAME = "GitSurfPreferences";
        String KEY_PREF_CODE_STYLE = "pref_code_style";

        interface User {
            String LOGIN = "USER_LOGIN";
            String TOKEN = "USER_TOKEN";
        }
    }

    interface UserListGroup {
        int FOLLOWING = 1;
        int FOLLOWERS = 2;
        int ORGANIZATIONS = 3;
        int MEMBERS = 4;
        int STARGAZERS = 5;
        int WATCHERS = 6;
        int CONTRIBUTORS = 7;
        int COLLABORATORS = 8;
    }

    interface IssueList {
        String STATE_ALL = "all";
    }

    interface RepositoryList {
        String FIELD_SORT = "sort";
        String FIELD_DIRECTION = "direction";
        String SORT_CREATED = "created";
        String SORT_UPDATED = "updated";
        String SORT_NAME = "full_name";
        String DIRECTION_ASCENDING = "asc";
        String DIRECTION_DESCENDING = "desc";
        String SORT_NEWEST = "newest";
        String SORT_OLDEST = "oldest";
        String SORT_STARGAZERS = "stargazers";
    }
}

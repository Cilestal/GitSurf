package ua.dp.michaellang.gitsurf.services.entity;

import org.eclipse.egit.github.core.IResourceProvider;

import java.util.List;

/**
 * Date: 13.04.17
 *
 * @author Michael Lang
 */
public class SearchUserList implements IResourceProvider<SearchUser> {
    private List<SearchUser> users;

    @Override
    public List<SearchUser> getResources() {
        return users;
    }
}

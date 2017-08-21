package ua.dp.michaellang.gitsurf.presenter;

/**
 * Date: 03.04.17
 *
 * @author Michael Lang
 */
public interface PagedPresenter extends BasePresenter {
    void loadItems();
    void loadMoreItems();
    void reloadItems();
}

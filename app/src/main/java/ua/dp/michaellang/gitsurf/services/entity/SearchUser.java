package ua.dp.michaellang.gitsurf.services.entity;

import org.eclipse.egit.github.core.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Date: 13.04.17
 *
 * @author Michael Lang
 */
public class SearchUser implements Serializable {
    private static final long serialVersionUID = -8228088036703861448L;

    /*
    * {
    "users": [
    {
      "id": "user-9981088",
      "gravatar_id": "",
      "username": "Cilestal",
      "login": "Cilestal",
      "name": "Michael Tarasyuk",
      "fullname": "Michael Tarasyuk",
      "location": "Dnipro",
      "language": "Java",
      "type": "user",
      "public_repo_count": 3,
      "repos": 3,
      "followers": 1,
      "followers_count": 1,
      "score": 23.478058,
      "created_at": "2014-11-27T20:19:35Z",
      "created": "2014-11-27T20:19:35Z"
    }
  ]
    }
    * */

    private String id;
    private String gravatarId;
    private String login;
    private String name;
    private String fullName;
    private String location;
    private String language;
    private String type;
    private int publicRepoCount;
    private int repos;
    private int followers;
    private int followersCount;
    private double score;
    private Date createdAt;
    private Date created;

    public SearchUser() {
    }

    public Date getCreatedAt() {
        return DateUtils.clone(this.createdAt);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGravatarId() {
        return gravatarId;
    }

    public void setGravatarId(String gravatarId) {
        this.gravatarId = gravatarId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPublicRepoCount() {
        return publicRepoCount;
    }

    public void setPublicRepoCount(int publicRepoCount) {
        this.publicRepoCount = publicRepoCount;
    }

    public int getRepos() {
        return repos;
    }

    public void setRepos(int repos) {
        this.repos = repos;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreated() {
        return DateUtils.clone(created);
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchUser that = (SearchUser) o;

        if (publicRepoCount != that.publicRepoCount) return false;
        if (repos != that.repos) return false;
        if (followers != that.followers) return false;
        if (followersCount != that.followersCount) return false;
        if (Double.compare(that.score, score) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (gravatarId != null ? !gravatarId.equals(that.gravatarId) : that.gravatarId != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        return created != null ? created.equals(that.created) : that.created == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (gravatarId != null ? gravatarId.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + publicRepoCount;
        result = 31 * result + repos;
        result = 31 * result + followers;
        result = 31 * result + followersCount;
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SearchUser{" +
                "id='" + id + '\'' +
                ", gravatarId='" + gravatarId + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", location='" + location + '\'' +
                ", language='" + language + '\'' +
                ", type='" + type + '\'' +
                ", publicRepoCount=" + publicRepoCount +
                ", repos=" + repos +
                ", followers=" + followers +
                ", followersCount=" + followersCount +
                ", score=" + score +
                ", createdAt=" + createdAt +
                ", created=" + created +
                '}';
    }
}


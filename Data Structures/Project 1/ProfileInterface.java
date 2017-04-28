/**
 * A very basic social media profile
 */
public interface ProfileInterface {
    /**
     * Sets the profile's name
     * @param name The new name
     */
    public void setName(String name);

    /**
     * Gets the profile's name
     * @return The name
     */
    public String getName();
    /**
     * Sets the profile's "about me" blurb
     * @param about The new blurb
     */
    public void setAbout(String about);

    /**
     * Gets the profile's "about me" blurb
     * @return The blurb
     */
    public String getAbout();

    /**
     * Adds another profile to this profile's following stack. The most obvious failure is when this
     * profile already follows the maximum number of other profiles. Although the stack may be
     * capable of holding duplicate items, this method should also return false if this profile is
     * already following other.
     * @param other The profile to follow
     * @return True if successful, false otherwise
	 */
	 
    public boolean follow(ProfileInterface other);

    /**
     * Removes the most recently-followed profile from this profile's following stack
     * @return The profile that was unfollowed
     */
	 
    public ProfileInterface unfollow();

    /**
     * Returns this profile's most recent follows
     * @param howMany The number of profiles to return
     * @return An array of size howMany, containing the most recently-followed profiles
	 */
    
    public ProfileInterface[] following(int howMany);

    /**
     * Recommend a profile for this profile to follow. For now, this can return any arbitrary
     * profile followed by one of this profile's followed profiles. For example, if this profile is
     * Alex, and Alex follows Bart, and Bart follows Crissy, this method might return Crissy. You
     * may implement more intelligent selection (e.g., ensuring that the selection is not already
     * followed), but it is not required.
     * @return The profile to suggest, or null if no suitable profile is found.
	 */
    
    public ProfileInterface recommend();
}


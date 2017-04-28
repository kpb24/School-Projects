
package videoengine;

/**
 * This abstract data type is a predictive engine for video ratings in a streaming video system. It
 * stores a set of users, a set of videos, and a set of ratings that users have assigned to videos.
 */
public interface VideoEngine {

    /**
     * Adds a new video to the system. This method checks to determine if the
     * system is full before attempting to add the video since it's a fixed size. It will 
     * throw an IndexOutOfBoundsException if the system is full and the video cannot be added. 
     * This method checks to see if the video is null and will throw a NullPointerException
	 * if that is the case.
     *
     * @param videoToAdd the video to be added to the system
     * @throws IndexOutOfBoundsException if the system is full
     * @throws NullPointerException if the video is null
     */
    public void addVideo(Video videoToAdd);


	
    /**
     * Removes a specified, existing video from the system. This method will first check to 
     * determine if the video is in the system before attempting removal. If the video exists 
     * in the system, it will be removed. If the video does not exist in the system, it will throw 
	 * an IllegalArgumentException.
     *
     * @param videoToRemove the video to be removed from the system
     * @throws IllegalArgumentException if the video does not exist in the system
     */
    public void removeVideo(Video videoToRemove);
    


    /**
     * Adds an existing television episode to an existing television series. This method will
	 * check if the both the episode and the series exist in the system. If one or both do not
	 * exist, it will throw an IllegalArgumentException. If the series
     * is full, the method will not attempt to add the episode to the series and will throw
     * an IndexOutOfBoundsException. If the episode is null, it will throw a NullPointerException.
     *
     * @param episodeToAdd the episode to add to the tv series
     * @param series the series to add the episode to
	 * @return the series that was added to
	 * @throws IllegalArgumentException if the episode or series does not exist in the system
     * @throws IndexOutOfBoundsException if the series is full
     * @throws NullPointerException if the episode is null
     */
    public TvSeries addToSeries(TvEpisode episodeToAdd, TvSeries series);



    /**
     * Removes a specified television episode from a television series. The method will first check
     * to make sure the episode is in the series before attempting removal. If the episode does not
     * exist in the series, then an IllegalArgumentException will be thrown. If the episode exists
     * in the series, then it will be removed.
     *
     * @param episodeToRemove the episode to be removed
     * @param series the series to remove the episode from
	 * @return the series that was removed from
     * @throws IllegalArgumentException if episode does not exist in the series
     */
    public TvSeries removeFromSeries(TvEpisode episodeToRemove, TvSeries series);



    /**
     * Sets a user's rating for a video, as a number of stars from 1 to 5. This method will
     * check to see if the video has already been rated by this user. If the video has
     * already been rated, it will throw an IllegalArgumentException. If the user or video
	 * is null then it will throw a NullPointerException. If the video has not already been rated, 
     * the user will be able to rate the video. 
     *
     * @param videoToRate the video to be rated
     * @param theUser the user doing the rating
     * @throws NullPointerException if the user or video is null
     * @throws IllegalArgumentException if the user has already rated this video
     */
    public void rateVideo(Video videoToRate, User theUser);



    /**
     * Clears a user's rating on a video. If this user has rated this video and the rating has not
     * already been cleared, then the rating is cleared and the state will appear as if the rating
     * was never made. If this user has not rated this video, or if the rating has already been
     * cleared, then this method will throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared
     * @param theVideo video from which the user's rating should be cleared
     * @throws IllegalArgumentException if the user does not currently have a rating on record for
     * the video
     * @throws NullPointerException if either the user or the video is null
     */
    public void clearRating(User theUser, Video theVideo);



    /**
     * Predicts the rating a user will assign to a video that they have not yet rated, as a number
     * of stars from 1 to 5. Returns a predicted rating from 1 to 5. This method will choose a
     * video that has not yet been rated. If the user has rated every video in the system, an
     * IllegalArgumentException will be thrown and a predicted rating will not be assigned.
     *
     * @param user the user to be given a predicted rating
	 * @return the predicted rating
     * @throws IllegalArgumentException if the user has rated every video in the system
     *
     */
    public int predictRating(User user);


    /**
     * Suggests a video for a user based on their predicted ratings. This method will
	 * return the suggested video. If the user does not have predicted ratings, it will
	 * throw an IllegalArgumentException.
     *
     * @param user the user who will be given the suggestion
     * @return the suggested video
	 * @throws IllegalArgumentException if the user does not have any predicted ratings
     */
    public Video suggestVideo(User user);


}


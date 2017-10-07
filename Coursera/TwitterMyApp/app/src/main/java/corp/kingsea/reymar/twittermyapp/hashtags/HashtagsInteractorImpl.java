package corp.kingsea.reymar.twittermyapp.hashtags;

/**
 * Created by reyma on 4/07/2016.
 */
public class HashtagsInteractorImpl implements HashtagsInteractor {

    private HashtagsRepository repository;

    public HashtagsInteractorImpl(HashtagsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getHashtags();
    }

}

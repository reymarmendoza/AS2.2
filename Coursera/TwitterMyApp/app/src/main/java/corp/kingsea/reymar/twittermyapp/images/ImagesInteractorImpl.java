package corp.kingsea.reymar.twittermyapp.images;

/**
 * Created by reyma on 3/07/2016.
 */
public class ImagesInteractorImpl implements ImagesInteractor{

    private ImagesRepository repository;

    public ImagesInteractorImpl(ImagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getImages();
    }
}

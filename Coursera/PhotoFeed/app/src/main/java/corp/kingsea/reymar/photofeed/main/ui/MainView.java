package corp.kingsea.reymar.photofeed.main.ui;

/**
 * aqui db estar contempladas las acciones que pueden ocurrir sobre la interfaz
 */
public interface MainView {
    //esta es la vista, que debe llamar al presentador
    void onUploadInit(); //la foto acaba de ser tomada
    void onUploadComplete();//la foto fue publicada
    void onUploadError(String error);//error en la carga

}

package View;

import javax.swing.ImageIcon;

/**
 * La classe affiche l'etat de chaque case
 */
public class ImageView {

    
    /*
     *  Variable pour chaque type/ soit le Blanc le noir ou choise
     */
    public static ImageIcon viewWHITEState = new ImageIcon(ImageView.class.getResource("/Images/WHITE.png")), 
                            viewBLACKState = new ImageIcon(ImageView.class.getResource("/Images/BLACK.png")),
                            viewChoiseState = new ImageIcon(ImageView.class.getResource("/Images/Choise.png"));

}

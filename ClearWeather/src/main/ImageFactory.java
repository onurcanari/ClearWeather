package main;

import javafx.scene.image.Image;

public class ImageFactory {
    ImageFactory(){
    }
    public Image getShape(int weatherCode){
        String imgUrl;
        switch (weatherCode){
            case 5:
            case 6:
            case 7:
                imgUrl="5";
                break;
            case 14:
            case 16:
            case 32:
            case 39:
                imgUrl=String.valueOf(weatherCode);
                break;
            case 27:
                imgUrl="partly_clear_night";
                break;
            case 28:
                imgUrl="partly_clear_day";
                break;
            case 29:
                imgUrl="partly_cloudy_night";
                break;
            case 30:
            case 34:
                imgUrl="partly_cloudy_day";
                break;
            default:
                imgUrl="clear_night";
                break;
        }
        return new Image(getClass().getResourceAsStream("img/"+imgUrl+".png"));
    }
}

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class RestrictedTextField extends TextField 
{
    private IntegerProperty maxLength = new SimpleIntegerProperty(this, "maxLength", -1);
 
    public RestrictedTextField()
    {
        textProperty().addListener(new ChangeListener<String>()
        {
            private boolean ignore;
 
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1)
            {
                if(ignore || s1 == null)
                {return;}
                if(maxLength.get() > -1 && s1.length() > maxLength.get()) 
                {
                    ignore = true;
                    setText(s1.substring(0, maxLength.get()));
                    ignore = false;
                }
            }
        });
    }
 
    public IntegerProperty maxLengthProperty() 
    {return maxLength;}
 
    public int getMaxLength()
    {return maxLength.get();}
 
    public void setMaxLength(int maxLength)
    {this.maxLength.set(maxLength);}
}

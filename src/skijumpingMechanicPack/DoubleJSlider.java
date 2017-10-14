package skijumpingMechanicPack;

import javax.swing.*;
import java.util.*;
import java.text.*;

/**
 * <b>Programm:</b> WaveGradient<br>
 * <b>Copyright:</b> 2002 Andreas Gohr, Frank Schubert<br>
 * <b>License:</b> GPL2 or higher<br>
 * <br>
 * <b>Info:</b> This JSlider uses doubles for its values
 */
public class DoubleJSlider extends JSlider{
  
	double max;
	double min;
  /**
   * Constructor - initializes with 0.0,100.0,50.0
   */
  public DoubleJSlider(){
    super();
    setDoubleMinimum(0.0);
    setDoubleMaximum(100.0);
    setDoubleValue(50.0);
  }
  
  /**
   * Constructor
   */
  public DoubleJSlider(int orientation, double min, double max, double val){
    super(orientation);
    setDoubleMinimum(min);
    setDoubleMaximum(max);
    setDoubleValue(val);

  }

	public void setMajorTickSpacing(double space) {
		Hashtable labelTable = new Hashtable();
		double range = max - min;
		int num = (int)(range / space);

		DecimalFormat angleFormat = new DecimalFormat("###.#");

		for (int i = 0; i <= num; i++) {
			double pos = min + (i * space);
			int key = (int)(pos * 100);
			String value = angleFormat.format(pos);
			labelTable.put(new Integer(key), new JLabel(value));
		}
		//super.setMajorTickSpacing(space * 100);
		this.setLabelTable(labelTable);
		this.setPaintLabels(true);
	}
  
  /**
   * returns Maximum in double precision
   */
  public double getDoubleMaximum() {
    return( getMaximum()/100.0 );
  }

  /**
   * returns Minimum in double precision
   */
  public double getDoubleMinimum() {
    return( getMinimum()/100.0 );
  }

  /**
   * returns Value in double precision
   */
  public double getDoubleValue() {
    return( getValue()/100 );
  }
  
  public String getDoubleValue(double value)
  {
      //String sValue = "0.00";
      String sValue = String.valueOf(value);
      return sValue;
  }
  
  /**
   * sets Maximum in double precision
   */
  public void setDoubleMaximum(double max) {
	this.max = max;
    setMaximum((int)(max*100));
  }
  
  /**
   * sets Minimum in double precision
   */
  public void setDoubleMinimum(double min) {
	this.min = min;
    setMinimum((int)(min*100));
  }
  
  /**
   * sets Value in double precision
   */
  public void setDoubleValue(double val) {
    setValue((int)(val*100));
    setToolTipText(Double.toString(val));
  }

}
import javax.swing.JFrame;

import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;

import com.echonest.api.v4.EchoNestException;

public class AudioWorld {
	AudioLoader audioLoader;
	public AudioWorld(AudioLoader audioLoader)
	{
	    this.audioLoader = audioLoader;	
	}
	
	public void simulateWorld()
	{
		
	}
	
	public void simulationTest2() throws EchoNestException
	{
		
		TestbedModel model = new TestbedModel();         // create our model
		
		
		
		// add tests
		//TestList.populateModel(model);                   // populate the provided testbed tests

		model.addCategory("AudioBike");             // add a category
		model.addTest(new MJWTest2(audioLoader));                // add our test
		
		

		// add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
		model.getSettings().addSetting(new TestbedSetting("My Range Setting", SettingType.ENGINE, 10, 0, 20));
		TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel
		JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
		testbed.setVisible(true);
		
		//change the size of the window
		testbed.setBounds(0, 0, 2000, 2000);
		testbed.setEnabled(true);
		
		
	
		
		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}
	
	
	
}

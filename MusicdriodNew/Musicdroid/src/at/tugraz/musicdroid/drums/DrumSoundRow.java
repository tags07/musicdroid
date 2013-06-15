package at.tugraz.musicdroid.drums;

import java.util.Observable;
import java.util.Observer;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import android.content.Context;
import android.util.Log;
import at.tugraz.musicdroid.SoundManager;

@Root
public class DrumSoundRow implements Observer {
	private Context context = null;
	private DrumsLayoutManager layoutManager = null;
	private DrumSoundRowLayout layout = null;
	@ElementArray
	private int[] beatArray = {0,0,0,0,0,0,0,0};
	@Element
	private int rawId = 0;
	@Element
	private int soundPoolId = 0;
	@Element
	private String soundRowName = null;
	
	//need this one for marshaling
	public DrumSoundRow() {}
	
	public DrumSoundRow(Context context, DrumsLayoutManager manager, int rowStringId, int soundRawId) {
		//super(context);
		this.context = context;
		this.layoutManager = manager;
		this.rawId = soundRawId;
		this.soundRowName = this.context.getResources().getString(rowStringId);
		
		layout = new DrumSoundRowLayout(this.context, this, this.soundRowName);
		
        soundPoolId = SoundManager.loadSound(soundRawId);
	}

	public void togglePosition(int position)
	{
		beatArray[position] = beatArray[position] == 0 ? 1 : 0;
	}
	
	public int getBeatArrayValueAtPosition(int position)
	{
		return beatArray[position];
	}
	
	@Override
	public void update(Observable observable, Object data) {
		int currentBeat = (Integer)data;
		//Log.i("DrumSoundRow", "Incoming Object: " + currentBeat);
		if(beatArray[currentBeat] == 1)
		{
			SoundManager.playSound(soundPoolId, 1, 1);
		}
	}
	
	public void setSoundPoolIdByDrumString(String drumString)
	{
		soundPoolId = SoundManager.loadSound(layoutManager.getRawIdByString(drumString));
		soundRowName = drumString;
	}
	
	public int getSoundPoolId()
	{
		return soundPoolId;
	}
	
	public int[] getBeatArray()
	{
		return beatArray;
	}
	
	public int getRawId()
	{
		return rawId;
	}
	
	public void setBeatArray(int[] beatArray)
	{
		this.beatArray = beatArray;
		layout.updateOnPresetLoad(this.beatArray);
	}
	
	public void setSoundPoolId(int spId)
	{
		soundPoolId =  spId;
	}
	
	public void setRawId(int rId)
	{
		rawId = rId;
	}
	
	public DrumSoundRowLayout getLayout()
	{
		return layout;
	}
	
	public String getSoundRowName()
	{
		return soundRowName;
	}
	
	public void setSoundRowName(String srn)
	{
		soundRowName = srn;
	}
	
	public void setSoundRowNameAndUpdateLayout(String srn)
	{
		Log.i("DrumSoundRow", "setSoundRowNameAndUpdateLayout " + soundRowName);
		if(!soundRowName.equals(srn))
		{
		  soundRowName = srn;
		  layout.setDrumSoundRowName(soundRowName);
		}
	}
	
}

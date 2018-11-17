package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Stack;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainUIController implements Initializable{
	Random r=new Random();
	static HashMap<Integer,String> t=new HashMap<>();
	static HashMap<Integer,String> t1=new HashMap<>();
	Stack<Integer> prev=new Stack<>();
	Connection conn=null;
	static int i=0;
	static Media media;
	static MediaPlayer mp;
	static MediaView mv;
	static JFXButton[] btn;
	double  totalTimeOfMusic;
	static int songno=0;
	@FXML
	AnchorPane ac;
	@FXML
	StackPane bigsc;
	@FXML
	Pane p;
	@FXML
	JFXButton close;
	@FXML
	JFXButton bgimage;
	@FXML
	JFXButton minimize;
	@FXML
	ImageView imageview;
	@FXML
	Label starttime;
	@FXML
	Label endtime;
	@FXML
	ProgressBar pb;
	@FXML
	JFXButton folderopen;
	@FXML
	JFXButton play;
	@FXML
	JFXButton clearlist;
	@FXML
	JFXButton info;
	@FXML
	JFXButton next;
	@FXML
	JFXButton previous;
	@FXML
	JFXButton drawerbutton;
	@FXML
	JFXButton fast;
	@FXML
	JFXButton slow;
	@FXML
	JFXButton search;
	@FXML
	JFXButton maximize;
	@FXML
	JFXButton searchclose;
	@FXML
	JFXTextField searchinput;
	@FXML
	JFXButton fav;
	@FXML
	BorderPane outer;
	@FXML
	BorderPane innerBP;
	@FXML
	JFXSlider volumeslider;
	@FXML
	Slider seekslider;
	@FXML 
	ImageView playbuttonview;
	@FXML
	ImageView drawerview;
	@FXML
	ImageView favview;
	@FXML
	VBox vb;
	VBox vb2=new VBox();
	@FXML
	Pane pane;
	@FXML
	ImageView speaker;
	@FXML
	JFXButton shuffle;
	@FXML
	ImageView shuffleview;
	@FXML
	JFXButton repeat;
	@FXML
	ImageView fastview;
	@FXML
	ImageView slowview;
	@FXML
	ImageView repeatview;
	static String currentsong;
	@FXML
	ScrollPane scpane;
	MediaView mediaView;
	@FXML
	JFXColorPicker cp;
	@FXML
	JFXToggleButton tb;
	@FXML
	Label l;
	@FXML
	Label logo;
	@FXML
	Label warning;
	boolean loadfavflag=false;
	static int repeatflag=0;
	static boolean shuffleflag=false;
	static boolean previousflag=false;
	boolean videoflag=false;
	int first=0;
	boolean infoflag=false;
	int slowflag=0;
	int fastflag=0;
	boolean searchflag=false;
	boolean drawerflag=false;
	
	
	Node img;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		img=innerBP.getCenter();
		setDB();
		checkBackground();
		settooTip();
		setdisable();
		checkToggle();
		
		
		Main.s.addEventHandler(KeyEvent.KEY_PRESSED,new  EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent a) {
				if(a.getCode()==KeyCode.SPACE) {
					play(); 
				}else if(a.getCode()==KeyCode.N) {
					next();
				}else if(a.getCode()==KeyCode.P) {
					previous();
				}else if(a.getCode()==KeyCode.F && !a.isControlDown()) {
					fast();
				}else if(a.getCode()==KeyCode.S && !a.isControlDown() && !a.isAltDown()) {
					slow();
				}else if(a.getCode()==KeyCode.R) {
					repeat();
				}else if(a.getCode()==KeyCode.S && a.isAltDown()) {
					shuffle();
				}else if(a.getCode()==KeyCode.C) {
					clearList();
				}else if(a.getCode()==KeyCode.S && a.isControlDown()) {
					search();
				}else if(a.getCode()==KeyCode.L) {
					loadFav();
					tb.setSelected(true);
				}else if(a.getCode()==KeyCode.F && a.isControlDown()) {
					favsong();
				}
				
				
			}});
		
		
		Main.s.requestFocus();
		
		
	}
	@FXML
	void fast() {
		
		Image image;
		if(fastflag==0) {
			mp.setRate(1.33);
			image=new Image("/photo/1xf.png");
			fastview.setImage(image);
			fastflag=1;
			}else if(fastflag==1) {
				mp.setRate(1.66);
				image=new Image("/photo/2xf.png");
				fastview.setImage(image);
				fastflag=2;
			}else if(fastflag==2) {
				mp.setRate(1.99);
				image=new Image("/photo/3xf.png");
				fastview.setImage(image);
				fastflag=3;
			}else if(fastflag==3) {
				mp.setRate(1);
				image=new Image("/photo/fast.png");
				fastview.setImage(image);
				fastflag=0;
			}
		image=new Image("/photo/slow.png");
		slowview.setImage(image);
		slowflag=0;
	}
	@FXML
	void slow() {
		Image image;
		if(slowflag==0) {
			mp.setRate(0.95);
			image=new Image("/photo/1xs.png");
			slowview.setImage(image);
			slowflag=1;
			}else if(slowflag==1) {
				mp.setRate(0.75);
				image=new Image("/photo/2xs.png");
				slowview.setImage(image);
				slowflag=2;
			}else if(slowflag==2) {
				mp.setRate(0.55);
				image=new Image("/photo/3xs.png");
				slowview.setImage(image);
				slowflag=3;
			}else if(slowflag==3) {
				mp.setRate(1);
				image=new Image("/photo/slow.png");
				slowview.setImage(image);
				slowflag=0;
			}
		image=new Image("/photo/fast.png");
		fastview.setImage(image);
		fastflag=0;
	}

	@FXML
	void search() {
		//System.out.println(searchflag);
		
		if(searchflag==false) {
		searchinput.setVisible(true);
		searchclose.setVisible(true);
		searchinput.requestFocus();
		
		searchflag=true;
		return;
		}else {
			String name=searchinput.getText();
//			if(name.isEmpty())
//				return;
			vb.getChildren().removeAll(btn);
			for(int j=0;j<i;j++) {
				if(btn[j].getText().toLowerCase().contains(name.toLowerCase())) {
					//System.out.println(btn[j].getText());
					vb.getChildren().add(btn[j]);
					
				}
			}
			if(drawerflag==false) {
			drawer();
			drawerflag=true;
			}
		}
		//
	}
	@FXML
	void searchclose() {
		searchinput.setVisible(false);
		searchclose.setVisible(false);
		if(drawerflag==true) {
		drawer();
		//drawerflag=false;
		}
		searchflag=false;
		vb.getChildren().removeAll(btn);
		addChildren();
	}
	
	void settooTip() {
		cp.setTooltip(new Tooltip("Set Background Color"));
		fav.setTooltip(new Tooltip("Select as Favorite"));
		fast.setTooltip(new Tooltip("Fast"));
		slow.setTooltip(new Tooltip("Slow"));
		search.setTooltip(new Tooltip("Search"));
		tb.setUserData("on");
		tb.setTooltip(new Tooltip("Load Favorite song on App Start"));
		drawerbutton.setTooltip(new Tooltip("Playlist"));
		folderopen.setTooltip(new Tooltip("Open Folder"));
		previous.setTooltip(new Tooltip("Previous"));
		next.setTooltip(new Tooltip("Next"));
		shuffle.setTooltip(new Tooltip("Shuffle Off"));
		clearlist.setTooltip(new Tooltip("Clear Playlist"));
		repeat.setTooltip(new Tooltip("Repeat All"));
		close.setTooltip(new Tooltip("Close"));
		info.setTooltip(new Tooltip("Info"));
		bgimage.setTooltip(new Tooltip("Set Background Image"));
		Image image=new Image("/photo/musiclogo1.png");
		imageview.setImage(image);
		Image drawerI=new Image("photo/songsclosed.png");
		drawerview.setImage(drawerI);
		Image i=new Image("/photo/favblack.png");
		favview.setImage(i);
		minimize.setOnAction(e -> {
		    ((Stage)((JFXButton)e.getSource()).getScene().getWindow()).setIconified(true);
		});
		
		ac.setOnMouseClicked(e->{
			if(e.getClickCount()==2&& videoflag==true) {
				maximize();
			}
		});
	}
	void loadFav() {
		if(loadfavflag==true)
			return;
		loadfavflag=true;
		//System.out.println("loadfav function");
		try {
			if(conn==null || conn.isClosed())
				conn=JDBCConnection.connector();
			Statement st=conn.createStatement();
			String q = null;
				q="SELECT * FROM song;";
				ResultSet rs=st.executeQuery(q);
				if(rs.next()) {
					
				String name=rs.getString("name");
				String path=rs.getString("path");
				//System.out.println(name+"  "+path);
				t.put(i,path);
            	t1.put(i,name);
            	i++;
				currentsong=path;
				//songno;
				while(rs.next()) {
					name=rs.getString("name");
					path=rs.getString("path");

	            	t.put(i,path);
	            	t1.put(i,name);
	            	i++;
					
				}
				q="UPDATE setting SET value='on' WHERE name='togglebutton';";
				//System.out.println(q);
				st.execute(q);
				if(btn!=null && vb!=null) {
					vb.getChildren().removeAll(btn);
				btn=null;
				}
				createButton();
				addChildren();
				setvisible();
				st.close();
				conn.close();
				mainPlay(songno);
				}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	void checkToggle() {
		
		
		try {
			if(conn==null || conn.isClosed())
			conn=JDBCConnection.connector();
			Statement st=conn.createStatement();
			String q="SELECT value FROM setting WHERE name='togglebutton';";
			ResultSet rs=st.executeQuery(q);
			String value=rs.getString("value");
			if(value.equals("on")) {
				tb.setSelected(true);
				
				
			}else {
				tb.setSelected(false);
				//return;
			}
			st.close();
			conn.close();
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		toggleButton();
	}
	@FXML
	void toggleButton() {
		
		if(tb.isSelected()) {
			
		loadFav();
		}else {
			
			//System.out.println("off");
			try {
				if(conn==null || conn.isClosed())
					conn=JDBCConnection.connector();
				Statement st=conn.createStatement();
				String q="UPDATE setting SET value='off' WHERE name='togglebutton';";
				st.execute(q);
				st.close();
				conn.close();
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}
			
	}
	void setcolor() {
		
		try {
			if(conn==null||conn.isClosed())
				conn=JDBCConnection.connector();
			Statement st=conn.createStatement();
			String q="SELECT value FROM setting WHERE name='color';";
			ResultSet r=st.executeQuery(q);
			String color=r.getString("value");
			//if(color==null)
				//="#f5f6f7";
			String s="-fx-background-color:"+color+";";
			outer.setStyle(s);
			//outer.setStyle("-fx-background-image:url(/photo/guitar.jpg);-fx-background-position:center ;\r\n" + 
				//	"-fx-background-size:cover;\r\n" + 
				//	"-fx-background-repeat: no-repeat ;");
			//Image image=new Image("/photo/music2.png");
			//imageview.setImage(image);
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@FXML
	void color() {
		Color c=cp.getValue();
		String s1=c.toString();
		s1=s1.replace("0x", "");
		String tmp="";
		for(int i=0;i<6;i++) {
			tmp=tmp.concat(String.valueOf(s1.charAt(i)));
		}
		s1="#";
		s1=s1.concat(tmp);
		//System.out.println(s1);
		String s="-fx-background-color:"+s1+";";
		outer.setStyle(s);
		

		try {
			if(conn==null || conn.isClosed())
				conn=JDBCConnection.connector();
			Statement st=conn.createStatement();
			String q="UPDATE setting SET value='"+s1+"' WHERE name='color';";
			st.execute(q);
			 q="UPDATE setting SET value='color' WHERE name='background';";
			st.execute(q);
			st.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	@FXML
	void setBackground() {
		FileChooser fc=new FileChooser();
		File file=fc.showOpenDialog(null);
		if(file==null)
			return;
		if(file.getName().endsWith(".png") || file.getName().endsWith(".PNG") || file.getName().endsWith(".JPG") || file.getName().endsWith(".jpg")|| file.getName().endsWith(".jpeg") || file.getName().endsWith(".JPEG")) {
			String path=file.getPath().replace(" ","%20");
			//System.out.println(path);
			path=path.replace("\\","/");
			String tmp="file:///";
			tmp=tmp.concat(path);
			path=tmp;
			//System.out.println(path);
			String style="-fx-background-image:url("+path+");"; // + 
				//	"	-fx-background-position: center ;\r\n" + 
				//	"	-fx-background-size: cover ;\r\n" + 
				//	"	-fx-background-repeat: no-repeat ;";
			//System.out.println(style);
			ac.setStyle(style);
			
			outer.setStyle("-fx-background-color:rgba(0,0,0,0.0)");
			try {
				if(conn==null || conn.isClosed())
					conn=JDBCConnection.connector();
					
				Statement st=conn.createStatement();
				String q="UPDATE setting SET value='image' WHERE name='background'; ";
				st.execute(q);
				 q="UPDATE setting SET value='"+path+"' WHERE name='image'; ";
				st.execute(q);
				st.close();
				conn.close();
				return;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
			
		}else {
			warning.setText("Select PNG or JPG images!");
			return;
		}
	}
	
	void checkBackground() {
		try {
			if(conn==null || conn.isClosed())
				conn=JDBCConnection.connector();
				
			Statement st=conn.createStatement();
			String q="SELECT value FROM setting WHERE name='background';";
			ResultSet rs=st.executeQuery(q);
			String value=rs.getString("value");
			if(value.equals("image")) {
				
				Statement st1=conn.createStatement();
				q="SELECT value FROM setting WHERE name='image';";
				ResultSet rs1=st1.executeQuery(q);
				value=rs1.getString("value");
				String style="-fx-background-image:url("+value+");";
				//System.out.println(value);
				ac.setStyle(style);
				st1.close();
			}else {

				st.close();
				
				conn.close();
				setcolor();
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	void setdisable() {
		play.setDisable(true);
		shuffle.setDisable(true);
		clearlist.setDisable(true);
		repeat.setDisable(true);
		drawerbutton.setDisable(true);
		seekslider.setDisable(true);
		volumeslider.setDisable(true);
		next.setDisable(true);
		previous.setDisable(true);
		fav.setDisable(true);
		fast.setDisable(true);
		slow.setDisable(true);
		searchinput.setVisible(false);
		searchclose.setVisible(false);
		search.setDisable(true);
	}
	void setvisible() {
		play.setTooltip(new Tooltip("Pause"));
		play.setDisable(false);
		shuffle.setDisable(false);
		clearlist.setDisable(false);
		repeat.setDisable(false);
		seekslider.setDisable(false);
		volumeslider.setDisable(false);
		drawerbutton.setDisable(false);
		next.setDisable(false);
		previous.setDisable(false);
		fav.setDisable(false);
		fast.setDisable(false);
		slow.setDisable(false);
		search.setDisable(false);
	}
	@FXML
	void repeat() {
		//System.out.println(repeatflag);
		if(repeatflag==0) {
			repeatflag=1;
			Image i=new Image("photo/repeat1.png");
			repeatview.setImage(i);
			repeat.setTooltip(new Tooltip("Repeat One"));
			
			}else if (repeatflag==1) {
				repeatflag=0;
				Image i=new Image("photo/repeatA.png");
				repeatview.setImage(i);
				repeat.setTooltip(new Tooltip("Repeat All"));
				
			}
	}
	@FXML
	void shuffle() {
		if(shuffleflag==false) {
			shuffleflag=true;
			Image i=new Image("photo/shufflegreen.png");
			shuffleview.setImage(i);
			shuffle.setTooltip(new Tooltip("Shuffle On"));
		}else if(shuffleflag==true) {
			shuffleflag=false;
			
			Image i=new Image("photo/shuffleblack.png");
			shuffleview.setImage(i);
			shuffle.setTooltip(new Tooltip("Shuffle Off"));
		}
	} 
	@FXML
	void clearList() {
		mp.stop();
//		for(int i=0;i<t1.size();i++) {
//			System.out.println(t1.get(i));
//		}
		t.clear();
		t1.clear();
		i=0;
		if(drawerflag==true) 
		drawer();
		//drawerflag=false;
		searchflag=false;
		tb.setSelected(false);
		loadfavflag=false;
		setdisable();
		
	}
	void playsong() {
		if(repeatflag==1) {
			//btn[songno].setStyle("-fx-background-color:rgba(0,0,0,0.0);-fx-text-fill:black;-fx-cursor:hand;");
			btn[songno].setStyle("-fx-background-color:orange;-fx-text-fill:white;-fx-cursor:hand;");
			mainPlay(songno);
		}else if(repeatflag==0 && shuffleflag==true && previousflag==false) {
			btn[songno].setStyle("-fx-background-color:rgba(0,0,0,0.0);-fx-text-fill:black;-fx-cursor:hand;");
			int random=r.nextInt(i);
			mainPlay(random);
			btn[songno].setStyle("-fx-background-color:orange;-fx-text-fill:white;-fx-cursor:hand;");
			
			
		}else if(previousflag==true) {
			btn[songno].setStyle("-fx-background-color:rgba(0,0,0,0.0);-fx-text-fill:black;-fx-cursor:hand;");
			if(!prev.isEmpty()) {
				//previous.setDisable(true);
				songno=(int)prev.pop();
			}
			
			//System.out.println(songno+"added");
			previousflag=false;
			btn[songno].setStyle("-fx-background-color:orange;-fx-text-fill:white;-fx-cursor:hand;");
			mainPlay(songno);
			
		}else{
			//System.out.println("last condition");
			btn[songno].setStyle("-fx-background-color:rgba(0,0,0,0.0);-fx-text-fill:black;-fx-cursor:hand;");
			songno=songno+1;
			if(songno>=i)
				songno=0;
			btn[songno].setStyle("-fx-background-color:orange;-fx-text-fill:white;-fx-cursor:hand;");
			mainPlay(songno);
			
		}
	}
	@FXML
	void next() {
		mp.stop();
		playsong();
	}
	@FXML
	void previous() {
		if(prev.isEmpty()) {
			//previous.setDisable(true);
			songno=0;
			//next();
		
		}else {
		prev.pop();
		mp.stop();
		
		}
		previousflag=true;
		playsong();
	}
	@FXML
	void selectfile() {
		FileChooser fc=new FileChooser();
		File file=fc.showOpenDialog(null);
		if(file==null)
			return;
		String Parent=file.getParent();
		String s=file.getPath();
		//System.out.println(file.getName());
		
		if (!s.endsWith(".mp3")&&!s.endsWith(".mp4")&&!s.endsWith(".aif")&&!s.endsWith(".aiff")&&!s.endsWith(".wav")&&!s.endsWith(".fxm")&&!s.endsWith(".flv")) {
			warning.setText("Select Mp3,Mp4 Files!");
			return; 
		}
		warning.setText("");
		s=s.replace("\\", "/");
		String t="file:///";
		s=t.concat(s);
		s=s.replaceAll(" ","%20");
		currentsong=s;
		//media=new Media(s);
		if(mp!=null)
		 mp.stop();
		if(btn!=null && vb!=null) {
			vb.getChildren().removeAll(btn);
		btn=null;
		//vb=new VBox();
		}
		// mp=new MediaPlayer(media);
		
		
		//  mediaView = new MediaView(mp);
		//System.out.println("after");
		
		addSong(Parent);
		 
			//seekslider.setValue(0.0);
			first=songno;
		mainPlay(songno);
		
		//seek(mp);
		
	}
	
	@FXML
	void maximize() {
		MediaView mv2=new MediaView(mv.getMediaPlayer());
		mv2.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
		
	    mv2.setPreserveRatio(true);
		    
	    Stage s=new Stage();
		BorderPane bp=new BorderPane();

		bp.setStyle("-fx-background-color:black;");
		Rectangle2D visualBound=Screen.getPrimary().getVisualBounds();
		bp.setPrefHeight(visualBound.getHeight());
		bp.setPrefWidth(visualBound.getWidth());
		bp.setCenter(mv2);
		
		Parent p= bp;
		Scene sc=new Scene(p);
		
		s.initStyle(StageStyle.UNDECORATED);
		 s.getIcons().add(new Image("/photo/musiclogo1.png"));
		s.setScene(sc);
		Main.s.hide();
		bp.setOnMousePressed( (e)->{
			if(e.getClickCount()==2) {
				s.close();
				
				Main.s.show();
				
			}
			
		});
		s.show(); 
	    
	  
	
	}
	
	double round(double d,int p) {
		if(p>0) {
			long factor=(long)Math.pow(10, p);
			d=d*factor;
			long tmp=Math.round(d);
			return (double)tmp/factor;
		}
			return d;
	}
	
	void seek(MediaPlayer mp) {
		
		
		
		
		
		mp.currentTimeProperty().addListener((Observable)->{
		     if(seekslider.isValueChanging()){
		    	 mp.seek(Duration.seconds((seekslider.getValue()*(totalTimeOfMusic)/100)));
		    
		     }
		     
		     
		     if(seekslider.isPressed()){
		    	 mp.seek(Duration.seconds((seekslider.getValue()*(totalTimeOfMusic)/100)));
		     }
		       
		     
		     seekslider.setValue((mp.getCurrentTime().toSeconds()*100)/totalTimeOfMusic);
		        
		     pb.setProgress(seekslider.getValue()/100);
		 
		        
		        
		        
		        
		        int minute=(int)mp.getCurrentTime().toSeconds()/60;
		        int second=(int)mp.getCurrentTime().toSeconds()%60;
		        starttime.setText(String.valueOf(minute)+":"+String.valueOf(second));
		      });
		
		 //volumeslider.setValue(40);
		    mp.setVolume(volumeslider.getValue() / 100);
		    volumeslider.valueProperty().addListener((Observable) -> {
		    //	System.out.println(volumeslider.getValue());
		    	
		        mp.setVolume(volumeslider.getValue() / 100);
		        if(volumeslider.getValue()<3) {
		        	Image i=new Image("photo/speaker0.png");
		        	speaker.setImage(i);
		        }else
		        if(volumeslider.getValue()>3&&volumeslider.getValue()<31) {
		        	Image i=new Image("photo/speaker30.png");
		        	speaker.setImage(i);
		        }else if(volumeslider.getValue()>30&&volumeslider.getValue()<61) {
		        	Image i=new Image("photo/speaker60.png");
		        	speaker.setImage(i);
		        }else if(volumeslider.getValue()>60) {
		        	Image i=new Image("photo/speaker90.png");
		        	speaker.setImage(i);
		        }

		       });
		    
		
			
			
			mp.setOnEndOfMedia(new Runnable(){

				@Override
				public void run() {
					// System.out.println("end of song");
					if(!prev.contains(songno)) {
						prev.add(songno);
					//	System.out.println(songno+"added");
						}
					 playsong();
				}
				  
		
			});
			
	}
	public static void extract(String p){
		//System.out.println(p);
        File f=new File(p);
        File l[]=f.listFiles();
       
        for(File x:l){
        
            if(x==null) return;
            if(x.isHidden()||!x.canRead()) continue;
            if(x.isDirectory()) {
            	String name=x.getName();
            	if(name.contains("[") || name.contains("]")|| name.contains("{")|| name.contains("}")) {
            		name=name.replace("{", "");
            		name=name.replace("}", "");
            		name=name.replace("[", "");
            		name=name.replace("]", "");
            		x.renameTo(new File(x.getParent().concat("\\")+name));
            		//System.out.println(x.getParent().concat("\\")+name);
            		//System.out.println(x.getName()+"---->"+x.getParent().concat("\\")+name);
            		
            	}
            	extract(x.getParent().concat("\\")+name);
            	
            	
            }
            else if((x.getName().endsWith(".mp3")||x.getName().endsWith(".mp4")||x.getName().endsWith(".aif")||x.getName().endsWith(".aiff")||x.getName().endsWith(".wav")||x.getName().endsWith(".fxm")||x.getName().endsWith(".flv")) && x.length()>1100000) {
               // System.out.println(x.getPath()+"\\"+x.getName());
            	//System.out.println(x.getName());
            	//System.out.println(x.getPath());
            	//System.out.println(x.getParent());
            	String name=x.getName();
            	if(name.contains("[") || name.contains("]")|| name.contains("{")|| name.contains("}")) {
            		name=name.replace("{", "");
            		name=name.replace("}", "");
            		name=name.replace("[", "");
            		name=name.replace("]", "");
            		x.renameTo(new File(x.getParent().concat("\\")+name));
            		//System.out.println(x.getName()+"---->"+x.getParent().concat("\\")+name);
            	}
            	String s=x.getPath();
            	s=s.replace("\\", "/");
        		String tmp="file:///";
        		s=tmp.concat(s);
        		s=s.replaceAll(" ","%20");
        		
        		if(!t1.containsValue(x.getName())) {
            	t.put(i,s);
            	t1.put(i,x.getName());
            	i++;
        		}
           // 	System.out.println(x.getName());
            	
			
            }
            
            
        }
        
        createButton();
        
      }
	static void createButton() {
	//   System.out.println("i"+i);
        btn=new JFXButton[i];
        for(int j=0;j<i;j++) {
        	btn[j]=new JFXButton(t1.get(j));
        	btn[j].setId(t.get(j));
        	final int k=j;
        //	System.out.println(t1.get(j));
        	btn[k].setOnMouseEntered((EvendHandler)->{
        		if(!btn[k].getId().equals(media.getSource()) || !btn[k].getId().equals(currentsong))
        		btn[k].setStyle("-fx-background-color:green;-fx-text-fill:white;-fx-cursor:hand;");
        		
        	});
        	btn[k].setOnMouseExited((EvendHandler)->{
        		if(!btn[k].getId().equals(media.getSource()) || !btn[k].getId().equals(currentsong))
        		btn[k].setStyle("-fx-background-color:rgba(0,0,0,0.0);-fx-text-fill:black;-fx-cursor:hand;");
        		
        	});
        	//System.out.println(btn[j].getId());
        	//System.out.println(currentsong);
        	if(btn[j].getId().equals(currentsong)) {
        		//System.out.println("true");
        		btn[j].setStyle("-fx-background-color:orange;-fx-text-fill:white;-fx-cursor:hand;");
        	}
        	//System.out.println(j);
        }
	}
	@FXML
	void close() {
		//System.out.println("close");
		if(conn!=null) {
			try {
				
				conn.close();
				conn=null;
				System.exit(0);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	int totaltime=0;
	void mainPlay(int sn) {
	//	System.out.println(sn);
		
		warning.setText("");
		Image image=new Image("/photo/slow.png");
		slowview.setImage(image);
		image=new Image("/photo/fast.png");
		fastview.setImage(image);
		slowflag=0;
		fastflag=0;
		if(songCheck(sn)) {
			Image i=new Image("/photo/favyellow.png");
			favview.setImage(i);
		}else {
			Image i=new Image("/photo/favblack.png");
					favview.setImage(i);
		}
		l.setText(btn[sn].getText());
		songno=sn;
		if(!prev.contains(songno)) {
			prev.add(songno);
			previous.setDisable(false);
		//	System.out.println(songno+"added");
			}
		currentsong=(btn[sn].getId());
		//System.out.println(currentsong);
		try {
		media=new Media(btn[sn].getId());
		//ImageView iv=new ImageView(new Image());
		//System.out.println(media.getMetadata().get("album"));
		}catch(Exception e) {
			
//			System.out.println(e.getMessage());
//			System.out.println(sn);
//			System.out.println(btn[sn].getId());
//			t.remove(sn);
//			t1.remove(sn);
			//vb.getChildren().remove(btn[sn]);
			
			favsong();
			clearList();
			loadFav();
			mp.stop();
			mp=null;
			//warning.setText(e.getMessage());
		
		}
		if(mp!=null)
			mp.stop();
		mp=new MediaPlayer(media);
		
		mp.setOnReady(new Runnable() {
			
			@Override
			public void run() {
				
				
				totalTimeOfMusic=media.getDuration().toSeconds();
				//totaltime=(int)totalTimeOfMusic;
				 //endtime.setText(String.valueOf(new DecimalFormat().format(new Duration(totalTimeOfMusic).toMinutes()))+"  /");
				int minute=(int)totalTimeOfMusic/60;
				int second=(int)totalTimeOfMusic%60;
				endtime.setText(String.valueOf(minute)+":"+String.valueOf(second));
				setvisible();
				Image i=new Image("photo/pause2.png");
				playbuttonview.setImage(i);
				//System.out.println(totalTimeOfMusic);
				//mp.play();
				//mp.setAutoPlay(true);

				
			}
			
		});
		//System.out.println(totaltime+"int");
		
		if(currentsong.endsWith(".mp4")||currentsong.endsWith(".fxm")||currentsong.endsWith(".flv")) {
			 mv=new MediaView(mp);
			mv.setFitHeight(255);
			videoflag=true;
			
			innerBP.setCenter(mv);
		}else {
			innerBP.setCenter(img);
			
			videoflag=false;
		}
		mp.play();
		mp.setAutoPlay(true);
		seek(mp);
		
	
		
		
	}
	void addSong(String path) {
		extract(path);
		addChildren();
		
		
	//	playsong(songno);
		
		//media=new Media(t.get(10));
	//	mp.stop();
		//mp=new MediaPlayer(media);
	//	mp.setAutoPlay(true);
	//	System.out.println(mp.getMedia().getSource());
	}
	void addChildren() {
		for( int j=0;j<i;j++) {
			//System.out.println(j+" "+t.get(j));
			final int k=j;
			String src=btn[k].getId();
			//System.out.println(src);
			//media=new Media(src);
			btn[k].setOnAction((ActionEvent ev)->{
				
				
				
				mp.stop();
				btn[songno].setStyle("-fx-background-color:rgba(0,0,0,0.0);-fx-text-fill:black;-fx-cursor:hand;");
				btn[k].setStyle("-fx-background-color:orange;-fx-text-fill:white;-fx-cursor:hand;");
				
				songno=k;
				currentsong=src;
				
				//mp=new MediaPlayer(media);
				//mp.play();
				//mp.setAutoPlay(true);
				//seek();
				mainPlay(songno);
				
				
			});
			if(src.equals(currentsong)) {
				songno=k;
				//System.out.println(songno+"Vineet//////");
				}
			vb.getChildren().add(btn[j]);
		}
	}
	
	@FXML
	void play() {
		if((slowflag!=0 || fastflag!=0) ) {
			Image image;
			mp.setRate(1);
			image=new Image("/photo/slow.png");
			slowview.setImage(image);
			image=new Image("/photo/fast.png");
			fastview.setImage(image);
			slowflag=0;
			fastflag=0;
			//System.out.println("play functoin");
		}else
		if(mp.getStatus()==Status.PLAYING) {
			mp.pause();
			Image i=new Image("photo/play.png");
			playbuttonview.setImage(i);
			play.setTooltip(new Tooltip("Play"));
		
		}else {
			mp.play();
			Image i=new Image("photo/pause2.png");
			playbuttonview.setImage(i);
			play.setTooltip(new Tooltip("Pause"));
		}
		
		
		

	}

	@FXML
	void drawer() {
		if(drawerflag==true) {
			Image i=new Image("photo/songsclosed.png");
			drawerview.setImage(i);
			scpane.setPrefWidth(3);
			vb.setPrefWidth(0);
		//	drawerbutton.setText(">");
			drawerflag=false;
			return;
		}
		Image i=new Image("photo/songs.png");
		drawerview.setImage(i);
	scpane.setPrefWidth(300);
	scpane.setPrefHeight(400);
	vb.setPrefWidth(300);
	drawerflag=true;
	//drawerbutton.setText("<");
	
	//scpane.setStyle("-fx-background-color:rgba(150,255,0,1);");
	//vb.setStyle("-fx-background-color:rgba(0,0,0,0.0);");
	//vb.setBackground(new Background( new BackgroundFill( Color.web( "#F4F4F4" ), CornerRadii.EMPTY, Insets.EMPTY ) ));

		
	}
	boolean songCheck(int sn) {
		
		try {
			if(conn==null||conn.isClosed())
				conn=JDBCConnection.connector();
			Statement st=conn.createStatement();
			
		//	System.out.println(btn[0]);
			String name=btn[sn].getText();
			name=name.replace("'", "''");
			String q="SELECT * FROM song WHERE name='"+name+"';";
			//System.out.println(q);
		
			ResultSet rs=st.executeQuery(q);
			
			if(rs.next()) {
				st.close();
				conn.close();
				return true;
			} else {
				st.close();
				conn.close();
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	@FXML
	void favsong() {
		if(songCheck(songno)) {
			
			try {
				if(conn==null || conn.isClosed())
					conn=JDBCConnection.connector();
				Statement st=conn.createStatement();
				String name=btn[songno].getText();
				name=name.replace("'", "''");
				String q="DELETE FROM song WHERE name='"+name+"';";
				st.execute(q);
				
				Image i=new Image("/photo/favblack.png");
				favview.setImage(i);
				st.close();
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return;
		}
		Image i=new Image("/photo/favyellow.png");
		favview.setImage(i);
		
		try {
			if(conn==null || conn.isClosed())
				conn=JDBCConnection.connector();
			Statement st=conn.createStatement();
			String name=btn[songno].getText();
			String path=btn[songno].getId();
			name=name.replace("'", "''");
			path=path.replace("'", "''");
			String q="INSERT INTO song(name,path) VALUES('"+name+"','"+path+ "');";
			//System.out.println(q);
			
			st.executeUpdate(q);
			st.close();
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	void setDB() {
		File f=new File("./DB");
		File f1=new File("./DB/FavSong.db");
		if(f.exists() && f1.exists()) {
			//System.out.println("exist");
		return;
		}
		JDBCConnection.createFolder();
		
		try {
			if(conn==null || conn.isClosed())
			conn=JDBCConnection.connector();
			Statement st=conn.createStatement();
			String q="CREATE TABLE song(name VARCHAR(1000),path VARCHAR(2000));";
			st.executeUpdate(q);
			q="CREATE TABLE setting(name VARCHAR(100),value VARCHAR(200));";
			st.executeUpdate(q);
			q="INSERT INTO setting(name,value) VALUES('togglebutton','off');";
			st.execute(q);
			q="INSERT INTO setting(name,value) VALUES('color','#f5f6f7');";
			st.execute(q);
			q="INSERT INTO setting(name,value) VALUES('background','image');";
			st.execute(q);
			q="INSERT INTO setting(name,value) VALUES('image','/photo/concert.jpg');";
			st.execute(q);
			st.close();
			conn.close();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	@FXML
	void info() throws IOException {
		if(infoflag==false) {
		Parent root=FXMLLoader.load((getClass().getResource("info.fxml")));
		Scene sc=new Scene(root);
		Stage st=new Stage();
		st.setScene(sc);
		st.setResizable(false);
		st.initStyle(StageStyle.UTILITY);
		st.show();
		st.setOnCloseRequest((WindowEvent)->{
			infoflag=false;
	
		});
		infoflag=true;
		}
		
	}
	
	
}




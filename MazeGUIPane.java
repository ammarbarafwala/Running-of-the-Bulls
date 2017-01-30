package lab9;

import java.util.HashMap;
import java.util.Random;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MazeGUIPane extends BorderPane {

	Coordinate fool;
	Label foolLabel,result;
	int index,row=25,column=25,numberofWalls=20*row*column/100,foolMoveCount;
	GridPane gp;
	Button b;
	HashMap<String, Integer> hm;
	Coordinate[][] coordinates;
	Bull bullObject1,bullObject2,bullObject3;
	public void startGame() {
		bullObject1=new Bull();
		bullObject2=new Bull();
		bullObject3=new Bull();
		gp=new GridPane();
		hm=new HashMap<>();
		coordinates=new Coordinate[row][column];
		index=1;
		foolMoveCount=0;
		Label[] label=createMazeLayout();
		StreetMap street=new StreetMap(coordinates);
		setCenter(gp);
		fool=new Coordinate(0, 1, 'S');
		runButtonAndKeyConfig(label);
		setPrefWidth(24*25);
		setPrefHeight(24*25);
	}
	
	private void runButtonAndKeyConfig(Label[] label) {
		// TODO Auto-generated method stub
		b=new Button("Run");
		result=new Label("");
		b.setOnAction(e->{
			
			if(fool.getColumn()!=1 && fool.getRow()!=0)
				resetGame();
			
			label[0].getStyleClass().clear();
			label[0].getStyleClass().add("fool");
			label[0].setText("  F ");
			foolLabel=label[0];
			
			setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					// TODO Auto-generated method stub
				if(fool.getRow()>-1)
				{
					if(event.getCode()==KeyCode.DOWN)
					{
						if(coordinates[fool.getRow()+1][fool.getColumn()].getCoordinateValue()!='W'){
							index=index+column;
							fool.setRow(fool.getRow()+1);
							moveTheFool();
						}
					}
					else if( event.getCode()==KeyCode.RIGHT)
					{
						if(coordinates[fool.getRow()][fool.getColumn()+1].getCoordinateValue()!='W'){
							index=index+1;
							fool.setColumn(fool.getColumn()+1);
							moveTheFool();
						}
					}
					else if(event.getCode()==KeyCode.UP)
					{
						if(coordinates[fool.getRow()-1][fool.getColumn()].getCoordinateValue()!='W'){
							index=index-column;
							fool.setRow(fool.getRow()-1);
							moveTheFool();
						}
					}
					else if(event.getCode()==KeyCode.LEFT)
					{
						if(coordinates[fool.getRow()][fool.getColumn()-1].getCoordinateValue()!='W'){
							index=index-1;
							fool.setColumn(fool.getColumn()-1);
							moveTheFool();
						}
					}
				}
			}});
		});
		HBox hbox=new HBox(b,result);
		hbox.setAlignment(Pos.CENTER);
		setBottom(hbox);
	}

	
	private void resetGame() {
		// TODO Auto-generated method stub
		getChildren().clear();
		startGame();
	}

	protected void moveTheFool() {
		// TODO Auto-generated method stub
		
			foolLabel.setText(" ");
			foolLabel.getStyleClass().clear();
			if(foolMoveCount>0)
				foolLabel.getStyleClass().add("space");
			else{
				foolLabel.getStyleClass().add("bull");
				foolLabel.setText(" B ");
			}
			foolLabel=(Label) gp.getChildren().get(index);
			foolLabel.setText("  F ");
			foolLabel.getStyleClass().clear();
			foolLabel.getStyleClass().add("fool");
			if(fool.getColumn()==column-2 && fool.getRow()==row-1){
				result.setText("Fool Wins");
				b.setText("Reset Game");
				fool.setColumn(-1);
				fool.setRow(-1);
			}
			foolMoveCount++;
			if(foolMoveCount>12){
				bullObject1.move(fool, hm, gp, column, coordinates,this);
				bullObject1.move(fool, hm, gp, column, coordinates,this);
				bullObject2.move(fool, hm, gp, column, coordinates,this);
				bullObject2.move(fool, hm, gp, column, coordinates,this);
				bullObject3.move(fool, hm, gp, column, coordinates,this);
				bullObject3.move(fool, hm, gp, column, coordinates,this);
				bullObject1.move(fool, hm, gp, column, coordinates,this);
				bullObject2.move(fool, hm, gp, column, coordinates,this);
				bullObject3.move(fool, hm, gp, column, coordinates,this);
			}
			//Checking if bull caught the fool
			if(bullObject1.getBullCoordinate().getColumn()==fool.getColumn()&& bullObject1.getBullCoordinate().getRow()==fool.getRow()){
				result.setText("Bull gored the fool");
				b.setText("Reset Game");
				fool.setRow(-1);
			}
			else if (bullObject2.getBullCoordinate().getColumn()==fool.getColumn()&& bullObject2.getBullCoordinate().getRow()==fool.getRow()) {
				result.setText("Bull gored the fool");
				b.setText("Reset Game");
				fool.setRow(-1);
			}
			else if (bullObject3.getBullCoordinate().getColumn()==fool.getColumn()&& bullObject3.getBullCoordinate().getRow()==fool.getRow()) {
				result.setText("Bull gored the fool");
				b.setText("Reset Game");
				fool.setRow(-1);
			}
	}

	private Label[] createMazeLayout() {
		// TODO Auto-generated method stub
		HashMap<String, Integer>hm=generateRandomWallLocation(numberofWalls);
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				Label l=new Label("");
				l.setMinSize(22, 22);
				l.setOnMouseClicked(e->{
					int c=GridPane.getColumnIndex(l);
					int r=GridPane.getRowIndex(l);
					if(c!=0 && r!=0 && c!=row-1 && r!=column-1){
						if(l.getStyleClass().contains("wall")){
							l.getStyleClass().clear();
							l.getStyleClass().add("space");
							coordinates[r][c].setCoordinateValue(' ');
						}
						else{
							l.getStyleClass().clear();
							l.getStyleClass().add("wall");
							coordinates[r][c].setCoordinateValue('W');
						}
					}
				});
				if(i==0||j==0||i==row-1||j==column-1||hm.get(i+"-"+j)!=null){
					coordinates[i][j]=new Coordinate(i, j, 'W');
					l.getStyleClass().add("wall");
				}
				else{
					l.getStyleClass().add("space");
					coordinates[i][j]=new Coordinate(i, j, ' ');
				}
				gp.add(l, j, i);
			}
		}
		Label startlabel=(Label) gp.getChildren().get(1);
		startlabel.getStyleClass().add("startAndEnd");
		coordinates[0][1]=new Coordinate(0, 1, 'S');
		startlabel.setText(" S ");
		Label endLabel=(Label) gp.getChildren().get(row*column-2);
		endLabel.setText(" E ");
		endLabel.getStyleClass().add("startAndEnd");
		coordinates[row-1][column-2]=new Coordinate(row-1, column-2, 'E');
		gp.setHgap(2);
		gp.setVgap(2);
		return new Label[]{startlabel,endLabel};
	
	}
	
	
	private HashMap<String, Integer> generateRandomWallLocation(int numberofWalls) {
		// TODO Auto-generated method stub
		
		Random random=new Random();
		for(int i=0;i<numberofWalls;i++)
		{
			int columnCell,rowCell;
			do{
				columnCell=random.nextInt(column-2)+1;
				rowCell=random.nextInt(row-2)+1;
			}while((columnCell<3 && rowCell<3) || (columnCell>column-4 && rowCell>row-4));
		hm.put(rowCell+"-"+columnCell, 0);
		}
		return hm;
	}	
}

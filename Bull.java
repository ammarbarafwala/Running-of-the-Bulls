package lab9;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Bull {

	Random r=new Random();
	Coordinate bullCoordinate=new Coordinate(0, 1, 'B');
	Label bullLabel;
	Coordinate lastFoolCoordinate;
	boolean wasFoolSeen=false;
	HashMap<String, Integer> bullTracker=new HashMap<>();
	ArrayList<Integer> directions;
	
	public void move(Coordinate foolCoordinate, HashMap<String, Integer> hm, GridPane gp, int totalColumns, Coordinate[][] co, MazeGUIPane mp)
	{
		bullLabel=(Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn());
		bullLabel.getStyleClass().add("bull");
		boolean flag=true;
		directions=new ArrayList<>();
		directions.add(0);
		directions.add(1);
		directions.add(2);
		directions.add(3);
		//Checking if fool is visible in same row line and moving toward it
		if(foolCoordinate.getRow()==bullCoordinate.getRow()){
			int n,i,row=foolCoordinate.getRow(),dif;
			if(foolCoordinate.getColumn()>bullCoordinate.getColumn()){
				dif=1;
				n=foolCoordinate.getColumn();
				i=bullCoordinate.getColumn()+1;
			}
			else{
				dif=-1;
				n=bullCoordinate.getColumn();
				i=foolCoordinate.getColumn()+1;
			}
			for(;i<n;i++)
			{
				if(hm.get(row+"-"+i)!=null)
					break;
			}
			if(i==n)
			{
				lastFoolCoordinate=new Coordinate(foolCoordinate.getRow(), foolCoordinate.getColumn(), 'F');
				wasFoolSeen=true;
				flag=false;
				bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()+dif));
				bullCoordinate.setColumn(bullCoordinate.getColumn()+dif);
			}
		}
		
		//Checking if fool is visible in same column line and moving toward it
		else if (foolCoordinate.getColumn()==bullCoordinate.getColumn()) {
			int n,i,column=foolCoordinate.getColumn(),dif,coordinate;
			if(foolCoordinate.getRow()>bullCoordinate.getRow()){
				dif=totalColumns;
				coordinate=1;
				n=foolCoordinate.getRow();
				i=bullCoordinate.getRow()+1;
			}
			else{
				dif=-totalColumns;
				coordinate=-1;
				n=bullCoordinate.getRow();
				i=foolCoordinate.getRow();
			}
			for(;i<n;i++)
			{
				if(hm.get(i+"-"+column)!=null)
					break;
			}
			if(i==n){
				lastFoolCoordinate=new Coordinate(foolCoordinate.getRow(), foolCoordinate.getColumn(), 'F');
				wasFoolSeen=true;
				flag=false;
				bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()+dif));
				bullCoordinate.setRow(bullCoordinate.getRow()+coordinate);
			}
		}
		//moving towards fools last known location
		if (wasFoolSeen && flag) {
			System.out.println(lastFoolCoordinate.getRow()+" "+lastFoolCoordinate.getColumn()+" "+bullCoordinate.getRow()+" "+bullCoordinate.getColumn());
			if(lastFoolCoordinate.getRow()==bullCoordinate.getRow()){
				if(bullCoordinate.getColumn()<lastFoolCoordinate.getColumn()){
					bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()+1));
					bullCoordinate.setColumn(bullCoordinate.getColumn()+1);
				}
				else if (bullCoordinate.getColumn()>lastFoolCoordinate.getColumn()) {
					bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()-1));
					bullCoordinate.setColumn(bullCoordinate.getColumn()-1);
				}
			}
			else if (lastFoolCoordinate.getColumn()==bullCoordinate.getColumn()) {
				if(bullCoordinate.getRow()<lastFoolCoordinate.getRow()){
					bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()+totalColumns));
					bullCoordinate.setRow(bullCoordinate.getRow()+1);
				}
				else if (bullCoordinate.getRow()>lastFoolCoordinate.getRow()) {
					bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()-totalColumns));
					bullCoordinate.setRow(bullCoordinate.getRow()-1);
				}	
			}
			flag=false;
			if(lastFoolCoordinate.getRow()==bullCoordinate.getRow() && lastFoolCoordinate.getColumn()==bullCoordinate.getColumn())
				wasFoolSeen=false;
		}
		
		//moving randomly if no last known location found
		if(flag)
			randomMove(gp, totalColumns,co);
		
		//checking if fool is visible to bull for its last location update
		if(foolCoordinate.getRow()==bullCoordinate.getRow()){
			int n,i,row=foolCoordinate.getRow();
			if(foolCoordinate.getColumn()>bullCoordinate.getColumn()){
				n=foolCoordinate.getColumn();
				i=bullCoordinate.getColumn()+1;
			}
			else{
				n=bullCoordinate.getColumn();
				i=foolCoordinate.getColumn()+1;
			}
			for(;i<n;i++)
			{
				if(hm.get(row+"-"+i)!=null)
					break;
			}
			if(i==n)
			{
				lastFoolCoordinate=new Coordinate(foolCoordinate.getRow(), foolCoordinate.getColumn(), 'F');
				wasFoolSeen=true;
			}
		}
		
		//Checking if fool is visible in same column line and moving toward it
		else if (foolCoordinate.getColumn()==bullCoordinate.getColumn()) {
			int n,i,column=foolCoordinate.getColumn();
			if(foolCoordinate.getRow()>bullCoordinate.getRow()){
				n=foolCoordinate.getRow();
				i=bullCoordinate.getRow()+1;
			}
			else{
				n=bullCoordinate.getRow();
				i=foolCoordinate.getRow();
			}
			for(;i<n;i++)
			{
				if(hm.get(i+"-"+column)!=null)
					break;
			}
			if(i==n){
				lastFoolCoordinate=new Coordinate(foolCoordinate.getRow(), foolCoordinate.getColumn(), 'F');
				wasFoolSeen=true;
			}
		}
		bullTracker.put(bullCoordinate.getRow()+"-"+bullCoordinate.getColumn(), 1);
	}
	
	//method which moves bull randomly
	private void randomMove(GridPane gp, int totalColumns, Coordinate[][] co){
		int index = 0,value = 1;
		
		char tempChar;
		boolean isNewPositionAvailable=true;
		
		do{
			int bullRow=bullCoordinate.getRow(),bullColumn=bullCoordinate.getColumn();
			if(bullCoordinate.getRow()!=0 || bullCoordinate.getColumn()!=1){
				if(!directions.isEmpty()){
					index=r.nextInt(directions.size());
					value=directions.get(index);
				}
				else{
					value=r.nextInt(4);
					isNewPositionAvailable=false;
				}
			}
			switch(value){
			case 0:
				tempChar=co[bullCoordinate.getRow()][bullCoordinate.getColumn()+1].getCoordinateValue();
				bullColumn=bullCoordinate.getColumn()+1;
				break;
			case 1:
				tempChar=co[bullCoordinate.getRow()+1][bullCoordinate.getColumn()].getCoordinateValue();
				bullRow=bullCoordinate.getRow()+1;
				break;	
			case 2:
				tempChar=co[bullCoordinate.getRow()][bullCoordinate.getColumn()-1].getCoordinateValue();
				bullColumn=bullCoordinate.getColumn()-1;
				break;
			default:
				tempChar=co[bullCoordinate.getRow()-1][bullCoordinate.getColumn()].getCoordinateValue();
				bullRow=bullCoordinate.getRow()-1;
			}
			System.out.println(directions.size()+"   "+value+"   "+bullTracker.containsKey(bullRow+"-"+bullColumn)+" "+(tempChar=='W')+"   "+bullRow+" "+bullColumn);
			if(!directions.isEmpty() && (bullTracker.containsKey(bullRow+"-"+bullColumn) || tempChar=='W')){
				directions.remove(index);
				isNewPositionAvailable=true;
			}
			else if (tempChar=='W') {
				isNewPositionAvailable=true;
			}
			else
				isNewPositionAvailable=false;
			
		}while(isNewPositionAvailable);
		System.out.println("Moved");
		switch(value){
			case 0:
				bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()+1));
				bullCoordinate.setColumn(bullCoordinate.getColumn()+1);
				break;
			case 1:
				bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()+totalColumns));
				bullCoordinate.setRow(bullCoordinate.getRow()+1);
				break;	
			case 2:
				bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()-1));
				bullCoordinate.setColumn(bullCoordinate.getColumn()-1);
				break;
			default:
				bullGUIMove(gp, totalColumns, (Label) gp.getChildren().get(bullCoordinate.getRow()*totalColumns+bullCoordinate.getColumn()-totalColumns));
				bullCoordinate.setRow(bullCoordinate.getRow()-1);
		}
	}
	
	private void bullGUIMove(GridPane gp, int totalColumns, Label tempLabel){
		bullLabel.getStyleClass().clear();
		bullLabel.setText("");
		if(bullCoordinate.getRow()>0)
			bullLabel.getStyleClass().add("space");
		else{
			bullLabel.getStyleClass().add("startAndEnd");
			bullLabel.setText(" S ");
		}
		bullLabel=tempLabel;
		bullLabel.setText(" B ");
		bullLabel.getStyleClass().clear();
		bullLabel.getStyleClass().add("bull");
	}

	public Coordinate getBullCoordinate() {
		return bullCoordinate;
	}
	
}

package main;
import java.util.*;

public class Maze {
	public static void main(String[] arges) {
		int size = 0;
		System.out.println("请输入需要生成的迷宫大小(最好为奇数):");
		Scanner mazesize=new Scanner(System.in);
		if(mazesize.hasNextInt()) {
			size = mazesize.nextInt();
			System.out.println("生成 "+size+" * "+size+" 的迷宫成功！");
		}
		int[][] maze= new int[size][size];
//		System.out.println(size);
//		迷宫全部重置为1
		for(int i=size-1;i>=0;i--) {
			for(int j=size-1;j>=0;j--) {
				maze[i][j]=1;
			}
		}
//		迷宫生成
		maze=mazeheadle(maze);
//		迷宫输出迷宫
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
					System.out.printf("%d ",maze[i][j]);
					if((j+1)%size==0) {
						System.out.println("");
					}
			}
		}
		mazesize.close();
		}
	
	
	public static int[][] mazeheadle(int[][] maze){
		ArrayList<Point> list = new ArrayList<Point>();
		int x_num=0,y_num = 0;
		int startx=1,starty=1;
		list.addAll(Aroundwallsc(maze, startx, starty, maze[0].length-1));
		maze[startx][starty]=0;
		while(!list.isEmpty()) {
			Point randwall=list.get(new Random().nextInt(list.size()));
			switch (randwall.direct) {
			case 2:
				x_num=randwall.x;
				y_num=randwall.y+1;
				break;
			case 4:
				x_num=randwall.x-1;
				y_num=randwall.y;
				break;
			case 6:
				x_num=randwall.x+1;
				y_num=randwall.y;
				break;
			case 8:
				x_num=randwall.x;
				y_num=randwall.y-1;
				break;
			}

			if(maze[x_num][y_num]==1) {//如果目标块是墙
				maze[x_num][y_num]=0;//打通目标块
				maze[randwall.x][randwall.y]=0;//打通墙
				list.addAll(Aroundwallsc(maze,x_num,y_num,maze[0].length-1));//再次计算周围的墙
			}
			list.remove(randwall);
		}
		return maze;
	}
	//扫描周围未打通的墙
	public static ArrayList<Point> Aroundwallsc(int[][] maze,int x,int y,int size){
		ArrayList<Point> list=new ArrayList<Point>();
		int xpos,ypos;
		if(y-1>0) {
			ypos=y-1;
			xpos=x;
			if(maze[xpos][ypos]==1) {
				list.add(new Point(xpos,ypos,8));
			}
		}
		if(y+1<size) {
			ypos=y+1;
			xpos=x;
			if(maze[xpos][ypos]==1) {
				list.add(new Point(xpos,ypos,2));
			}
		}
		if(x+1<size) {
			ypos=y;
			xpos=x+1;
			if(maze[xpos][ypos]==1) {
				list.add(new Point(xpos,ypos,6));
			}
		}
		if(x-1>0) {
			ypos=y;
			xpos=x-1;
			if(maze[xpos][ypos]==1) {
				list.add(new Point(xpos,ypos,4));
			}
		}
//		for(Point xxx:list) {
//			System.out.printf("%d  %d  %d",xxx.x,xxx.y,xxx.direct);
//		}
		return list;
	}
	}
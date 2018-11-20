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
		int [][] Originalmaze=new int[size][size];
//		迷宫输出迷宫
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
					System.out.printf("%d ",maze[j][i]);
					Originalmaze[j][i]=maze[j][i];
					if((j+1)%size==0) {
						System.out.println("");
					}
			}
		}
		mazesize.close();
//		深度优先进行寻路
		Stack<Pos> posstack=new Stack<Pos>();
		posstack.push(new Pos(1,1));
		int endposx=maze[0].length-2;
		int endposy=maze[0].length-2;
		Pos nowpos=new Pos(1,1);
		int[][] alrmaze=maze;
		alrmaze[1][1]=1;
		while(!posstack.isEmpty()&&(nowpos.x!=endposx||nowpos.y!=endposy)) {
			int i=1;
			int xp=nowpos.x,yp=nowpos.y;
			while(i<=5) {
				if(i==1) {
					//向上
					if(maze[xp][yp-1]==0&&alrmaze[xp][yp-1]!=1) {
						posstack.push(new Pos(xp,yp-1));
						alrmaze[xp][yp-1]=1;
						break;
					}
				}
				if(i==2) {
					//向右
					if(maze[xp+1][yp]==0&&alrmaze[xp+1][yp]!=1) {
						posstack.push(new Pos(xp+1,yp));
						alrmaze[xp+1][yp]=1;
						break;
					}
				}
				if(i==3) {
					//向下
					if(maze[xp][yp+1]==0&&alrmaze[xp][yp+1]!=1) {
						posstack.push(new Pos(xp,yp+1));
						alrmaze[xp][yp+1]=1;
						break;
					}
				}
				if(i==4) {
					//向左
					if(maze[xp-1][yp]==0&&alrmaze[xp-1][yp]!=1) {
						posstack.push(new Pos(xp-1,yp));
						alrmaze[xp-1][yp]=1;
						break;
					}
					
				}
				if(i==5) {
					posstack.pop();
					break;
				}
				i++;
			}
			nowpos.x=posstack.peek().x;
			nowpos.y=posstack.peek().y;
//			System.out.printf("%d  %d \n",nowpos.x,nowpos.y);
		}
//		广度优先进行寻路
		
		int[][] dir = {{1, 0}, {0, 1},{-1, 0}, {0, -1}};
        int[][] visited=new int[maze[0].length][maze[0].length];
        Node start=new Node(1,1);
        Node end=new Node(maze[0].length-2,maze[0].length-2);
        Queue<Node> queue=new LinkedList<Node>();
        ArrayList<Node> arrayList=new ArrayList<Node>();//用来保存每一个出队列的节点
        queue.offer(start);
        while (!queue.isEmpty()){
            Node local=queue.remove();
            arrayList.add(local);
            if(local.x==end.x&&local.y==end.y) {
            	break;
            }
            for (int i=0;i<4;i++){
                Node nbr=new Node(local.x+dir[i][0],local.y+dir[i][1]);
                if(nbr.x>=1&&nbr.x<maze[0].length&&nbr.y>=1&&nbr.y<maze[0].length&&Originalmaze[nbr.x][nbr.y]==0&&visited[nbr.x][nbr.y]==0){
                    visited[nbr.x][nbr.y]=1;
                    queue.offer(nbr);
                    nbr.prex=local.x;//保存前驱节点
                    nbr.prey=local.y;//保存前驱节点
                }
            }
        }
        Stack<Integer> stack=new Stack<Integer>();
        int  px=arrayList.get(arrayList.size()-1).prex;//获得目的节点的前驱节点
        int  py=arrayList.get(arrayList.size()-1).prey;
        stack.push(arrayList.size()-1);//将目的节点在arrayList中的位置记录下来，便于输出
        while (true){
            if(px==1&&py==1){//找到起始节点就停止
                break;
            }
            for(int i=0;i<arrayList.size();i++){//循环找出每一个节点的前驱，找到就跳出当前循环
                if(arrayList.get(i).x==px&&arrayList.get(i).y==py){
                    px=arrayList.get(i).prex;
                    py=arrayList.get(i).prey;
                    stack.push(i);//保存节点在arrayList中的位置
                    break;
                }
            }
        }
        System.out.println("(1,1)");
        while (!stack.isEmpty()){
            System.out.println("("+arrayList.get(stack.peek()).x+","+arrayList.get(stack.peek()).y+")");
            stack.pop();
        }

		}
			
		
//		A*算法进行寻路
		
	
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
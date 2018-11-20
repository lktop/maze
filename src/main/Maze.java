package main;
import java.util.*;

public class Maze {
	public static void main(String[] arges) {
		int size = 0;
		System.out.println("��������Ҫ���ɵ��Թ���С(���Ϊ����):");
		Scanner mazesize=new Scanner(System.in);
		if(mazesize.hasNextInt()) {
			size = mazesize.nextInt();
			System.out.println("���� "+size+" * "+size+" ���Թ��ɹ���");
		}
		int[][] maze= new int[size][size];
//		System.out.println(size);
//		�Թ�ȫ������Ϊ1
		for(int i=size-1;i>=0;i--) {
			for(int j=size-1;j>=0;j--) {
				maze[i][j]=1;
			}
		}
//		�Թ�����
		maze=mazeheadle(maze);
		int [][] Originalmaze=new int[size][size];
//		�Թ�����Թ�
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
//		������Ƚ���Ѱ·
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
					//����
					if(maze[xp][yp-1]==0&&alrmaze[xp][yp-1]!=1) {
						posstack.push(new Pos(xp,yp-1));
						alrmaze[xp][yp-1]=1;
						break;
					}
				}
				if(i==2) {
					//����
					if(maze[xp+1][yp]==0&&alrmaze[xp+1][yp]!=1) {
						posstack.push(new Pos(xp+1,yp));
						alrmaze[xp+1][yp]=1;
						break;
					}
				}
				if(i==3) {
					//����
					if(maze[xp][yp+1]==0&&alrmaze[xp][yp+1]!=1) {
						posstack.push(new Pos(xp,yp+1));
						alrmaze[xp][yp+1]=1;
						break;
					}
				}
				if(i==4) {
					//����
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
//		������Ƚ���Ѱ·
		
		int[][] dir = {{1, 0}, {0, 1},{-1, 0}, {0, -1}};
        int[][] visited=new int[maze[0].length][maze[0].length];
        Node start=new Node(1,1);
        Node end=new Node(maze[0].length-2,maze[0].length-2);
        Queue<Node> queue=new LinkedList<Node>();
        ArrayList<Node> arrayList=new ArrayList<Node>();//��������ÿһ�������еĽڵ�
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
                    nbr.prex=local.x;//����ǰ���ڵ�
                    nbr.prey=local.y;//����ǰ���ڵ�
                }
            }
        }
        Stack<Integer> stack=new Stack<Integer>();
        int  px=arrayList.get(arrayList.size()-1).prex;//���Ŀ�Ľڵ��ǰ���ڵ�
        int  py=arrayList.get(arrayList.size()-1).prey;
        stack.push(arrayList.size()-1);//��Ŀ�Ľڵ���arrayList�е�λ�ü�¼�������������
        while (true){
            if(px==1&&py==1){//�ҵ���ʼ�ڵ��ֹͣ
                break;
            }
            for(int i=0;i<arrayList.size();i++){//ѭ���ҳ�ÿһ���ڵ��ǰ�����ҵ���������ǰѭ��
                if(arrayList.get(i).x==px&&arrayList.get(i).y==py){
                    px=arrayList.get(i).prex;
                    py=arrayList.get(i).prey;
                    stack.push(i);//����ڵ���arrayList�е�λ��
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
			
		
//		A*�㷨����Ѱ·
		
	
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

			if(maze[x_num][y_num]==1) {//���Ŀ�����ǽ
				maze[x_num][y_num]=0;//��ͨĿ���
				maze[randwall.x][randwall.y]=0;//��ͨǽ
				list.addAll(Aroundwallsc(maze,x_num,y_num,maze[0].length-1));//�ٴμ�����Χ��ǽ
			}
			list.remove(randwall);
		}
		return maze;
	}
	//ɨ����Χδ��ͨ��ǽ
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
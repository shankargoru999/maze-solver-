package GUI;

import codeFiles.dfsAlogorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class View extends JFrame implements ActionListener, MouseListener {
    /*
    values
    0-not visited;
    1-blocked wall;
    2-visited blocks;
    9-target;
     */
           private int [][] maze =
            { {1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,1,0,1,0,1,0,0,0,0,0,1},
                    {1,0,1,0,0,0,1,0,1,1,1,0,1},
                    {1,0,0,0,1,1,1,0,0,0,0,0,1},
                    {1,0,1,0,0,0,0,0,1,1,1,0,1},
                    {1,0,1,0,1,1,1,0,1,0,0,0,1},
                    {1,0,1,0,1,0,0,0,1,1,1,0,1},
                    {1,0,1,0,1,1,1,0,1,0,1,0,1},
                    {1,0,0,0,0,0,0,0,0,0,1,9,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1}

            };
    private int[]target={8,11};
    private List<Integer> path=new ArrayList<>();
    JButton submitbutton;
    JButton clearbutton;
    JButton cancelbutton;


    public View(){
        this.setTitle("MAZE SOLVER");
        this.setSize(520,500);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        submitbutton=new JButton("submit");
        submitbutton.addActionListener(this);
        submitbutton.setBounds(120,400,80,30);

        clearbutton=new JButton("clear");
        clearbutton.addActionListener(this);
        clearbutton.setBounds(200,400,80,30);

        cancelbutton=new JButton("cancel");
        cancelbutton.addActionListener(this);
        cancelbutton.setBounds(280,400,80,30);
        this.addMouseListener(this);
        this.add(submitbutton);
        this.add(clearbutton);
        this.add(cancelbutton);

    }
    public void paint (Graphics g){
        super.paint(g);
        for(int row=0;row< maze.length;row++){
            for(int col=0;col<maze[0].length;col++){
                Color color;
                switch(maze[row][col]){
                    case 1:color=Color.BLACK;break;
                    case 9:color=Color.RED;break;
                    default:color=Color.WHITE;
                }
                g.setColor(color);
                g.fillRect(40*col,40*row,40,40);
                g.setColor(Color.BLACK);
                g.drawRect(40*col,40*row,40,40);

            }
        }
        for(int p=0;p< path.size();p+=2){
            int pathx=path.get(p);
            int pathy=path.get(p+1);
            g.setColor(Color.GREEN);
            g.fillRect(40*pathy,40*pathx,40,40);
        }
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==submitbutton){
            try{
                boolean result= dfsAlogorithm.searchPath(maze,1,1,path);
                System.out.print(result);
                this.repaint();
            }
            catch(Exception exp){
                JOptionPane.showMessageDialog(null,exp.toString());
            }
        }
        if(e.getSource()==cancelbutton){
            int response=JOptionPane.showConfirmDialog(null,"do u want to exit","submit",JOptionPane.YES_NO_OPTION);
            if(response==0){
                System.exit(0);
            }
        }
        if(e.getSource()==clearbutton){
            for(int row=0;row<maze.length;row++){
                for(int col=0;col<maze[0].length;col++){
                    if(maze[row][col]==2){
                        maze[row][col]=0;
                    }
                }
            }
            path.clear();
            this.repaint();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){
        if(e.getX()>=0 && e.getX()<=maze[0].length*40 && e.getY()>=0 && e.getY()<=maze.length*40){
            int x=e.getY()/40;
            int y=e.getX()/40;
            if(maze[x][y]==1){
                return;
            }
            Graphics g=getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(40*target[1],40*target[0],40,40);
            g.setColor(Color.RED);
            g.fillRect(40*y,40*x,40,40);
            maze[target[0]][target[1]]=0;
            maze[x][y]=9;
            target[0]=x;
            target[1]=y;
        }
    }
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }


    public static void main(String[] args) {
        View view =new View();
        view.setVisible(true);
    }

}

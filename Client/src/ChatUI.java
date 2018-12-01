import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ChatUI {
	private ChatClient client;
	private ChatServerInt server;
	public void doConnect() {
		if (connect.getText().equals("Connecter")) {
			if (name.getText().length()<2) {
				JOptionPane.showMessageDialog(frame, "Vous devez entrer un nom");
				return;
			}
//			if (ip.getText().length()<2) {
//				JOptionPane.showMessageDialog(frame, "Vous devez entrer une addresse IP");
//				return;
//			}
			try {
				client=new ChatClient(name.getText());
				client.setGUI(this);
				server=(ChatServerInt)Naming.lookup("rmi://"+ip.getText()+"/myabc");
				server.login(client);
				updateUsers(server.getConnected());
				connect.setText("Déconnecter");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				JOptionPane.showMessageDialog(frame, "ERREUR, Problème de connexion .....");
			}
		}else {
			updateUsers(null);
			connect.setText("Connecter");
		}
	}
	public void sendText() {
		if (connect.getText().equals("Connecter")) {
			JOptionPane.showMessageDialog(frame, "Vous devez vous connecter d'abord.");
			return;
		}
		String st = tf.getText();
		st="["+name.getText()+"] "+st;
		tf.setText("");
		try {
			server.publish(st);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void writeMsg(String st) {
		tx.setText(tx.getText()+"\n"+st);
	}
	public void updateUsers(Vector v){
	      DefaultListModel listModel = new DefaultListModel();
	      if(v!=null) for (int i=0;i<v.size();i++){
	    	  try{  
	    		  String tmp=((ChatClientInt)v.get(i)).getName();
	    	  	  listModel.addElement(tmp);
	    	  }catch(Exception e){e.printStackTrace();}
	      }
	      lst.setModel(listModel);
	  }
	public static void main(String [] args){
		System.out.println("Hello World !");
		ChatUI c=new ChatUI();
	  }
	public ChatUI(){
	    frame=new JFrame("Groupe Chat");
	    JPanel main =new JPanel();
	    JPanel top =new JPanel();
	    JPanel cn =new JPanel();
	    JPanel bottom =new JPanel();
	    ip=new JTextField("127.0.0.1");
	    tf=new JTextField();
	    name=new JTextField();
	    tx=new JTextArea();
	    connect=new JButton("Connecter");
	    JButton bt=new JButton("Envoyer");
	    lst=new JList();        
	    main.setLayout(new BorderLayout(5,5));         
	    top.setLayout(new GridLayout(1,0,5,5));   
	    cn.setLayout(new BorderLayout(5,5));
	    bottom.setLayout(new BorderLayout(5,5));
	    top.add(new JLabel("Votre nom: "));top.add(name);    
	    top.add(new JLabel("@ IP Serveur: "));top.add(ip);
	    top.add(connect);
	    cn.add(new JScrollPane(tx), BorderLayout.CENTER);        
	    cn.add(lst, BorderLayout.EAST);    
	    bottom.add(tf, BorderLayout.CENTER);    
	    bottom.add(bt, BorderLayout.EAST);
	    main.add(top, BorderLayout.NORTH);
	    main.add(cn, BorderLayout.CENTER);
	    main.add(bottom, BorderLayout.SOUTH);
	    main.setBorder(new EmptyBorder(10, 10, 10, 10) );
	    //Events
	    connect.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){ doConnect();   }  });
	    bt.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){ sendText();   }  });
	    tf.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent e){ sendText();   }  });
	    
	    frame.setContentPane(main);
	    frame.setSize(600,600);
	    frame.setVisible(true);  
	  }
	  JTextArea tx;
	  JTextField tf,ip, name;
	  JButton connect;
	  JList lst;
	  JFrame frame;
}

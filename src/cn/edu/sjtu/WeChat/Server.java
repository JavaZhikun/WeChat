package cn.edu.sjtu.WeChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 	�����ҷ����
 *  ��������
 * @author Administrator
 * 
 */

public class Server
{
	/**
	 * java.net.ServerSocket
	   �����ڷ�������Socket�� ��Ҫ����������������˿ڣ��������ö˿�
	   �ȴ��ͻ��˵����ӣ�һ��һ���ͻ�����������������ӣ�ServerSocket�ͻ᷵�ظÿͻ��˹�ͨ��Socket
	 */
	
	private ServerSocket server;
	
	
	/**
	 * ���췽����������ʼ��������������
	 */
	//ԭ��ʹ��IO������ͨѶ������д�����쳣
	public Server(){
		try{
			/**
			 * ����ServerSocket��ͬʱָ������ķ���
			 * �˿ڣ��ͻ��˾���ͨ������˿ں������Ϸ���˵�
			 */
			
			//���з���ˣ�Ȼ����пͻ��ˣ����������з���ˣ�Ȼ���ٿ�ʼ�ͻ���
			server = new ServerSocket(8088);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * ����˿�ʼ�����ķ���
	 */
	public void start(){
		try{
			/**
			 * ServerSocket ��accept����:
			 * Socket accept()
			 * �÷���������������˴򿪵�8088�˿ڣ�
			 * �ȴ��ͻ��˵����ӣ�һ��һ���ͻ��������˾ͻ᷵��
			 * ��ͻ��˽���ͨ�ŵ�Socket
			 * 
			 * accept��һ������������ֱ��һ���ͻ������ӣ�����ͻ�һֱ��������
			 */
			while(true){//�ȴ��ͻ���
				System.out.println("�ȴ��ͻ�������");
				Socket socket = server.accept();
				System.out.println("һ���ͻ���������");
				/**
				 * ����һ���̣߳��������ո����ӵĿͻ���
				 * �Ľ�������
				 */
				ClientHandler handler= new ClientHandler(socket);
				Thread t = new Thread(handler);
				t.start();			
			}
	      }catch(Exception e){
	    	  e.printStackTrace();
	      }
	    	  
	}
	
	public static void main(String[] args)
	{
		Server server = new Server();
		server.start();
	}
	
	/**
	 * ���߳�������������Ŀͻ��˵Ľ�������
	 * 
	 *
	 */
	class ClientHandler implements Runnable{
		
		
		
		
		//��ǰ�߳����������Ŀͻ��˵�Socket
		private Socket socket;
		/**
		 * �ÿͻ��˵ĵ�ַ
		 * @param socket
		 */
		 private String host;
		public ClientHandler(Socket socket){
			/**
			 *  ͨ��
			 */
			InetAddress address = socket.getInetAddress();
			//ͨ������IP
			String host = address.getHostAddress();
			//Զ�̼���������Ķ˿�
			int port = socket.getPort();
			System.out.println(host + "������");
			this.socket = socket;//��socket����ClientHandler����ȥ��
		}
		
		public void run(){
			try{
				/**
				 * socket�ķ���getInputStream
				 * InputStream getInputStream
				 * �÷���������ȡһ�������������Զ�ȡ����Զ�̼�������͹���������
				 */
				
//				InputStream in = socket.getInputStream();
//				InputStreamReader isr = new InputStreamReader(in,"UTF-8");
//				BufferedReader br =new  BufferedReader(isr);
//				String message = null;
//				while((message = br.readLine())!= null){
//					System.out.println("�ͻ���˵" + message);
//				}
				
				
				
				
				
				InputStream  in = socket.getInputStream();
				//ʹ�øø߼�����Ŀ�����ڣ����Խ�in�ж�ȡ���ֽ���UTF-8��������ַ�
				InputStreamReader isr = new InputStreamReader(in,"UTF-8");
				//ת���ɻ����ַ��������ĺô����ڣ���������Ϊ��λ���ж�ȡ�ַ����ˡ�
				BufferedReader br = new BufferedReader(isr);
				String message = null;
				//��ȡ�ͻ��˷��͹�����һ���ַ���
				/*
				 * �����ȡ�ͻ��˷��͹�����һ���ַ�����br.readLine()���������ڿͻ��˵Ĳ���
				 * ϵͳ��ͬ�����¿ͻ�����������Ͽ����Ӻ��������Ч���ǲ�ͬ�ġ�
				 * 
				 * Linux: ��Linux �Ŀͻ�����������Ͽ����Ӻ�br.readLine(�������᷵��null
				 * windows: ��windows�Ŀͻ�����������Ͽ����Ӻ�br.readLine()����
				 *          ���׳��쳣
				 */
				while((message = br.readLine())!= null){
					System.out.println(host +"˵��" + message);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{//��������������ִ��
				System.out.println(host +"������");
				/**
				 * ������linux�ͻ��˻���windows�Ŀͻ��ˣ�
				 * �������ǶϿ����Ӻ󣬶�Ӧ�ý�Socket�رգ����ͷ���Դ��
				 * 
				 * ������socket��������
				 */
				try
				{
					socket.close();//�����ٵĵײ�Ķ������ͷŵ�
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				
			}
		}
	}
}

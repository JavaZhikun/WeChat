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
 * 	聊天室服务端
 *  服务器端
 * @author Administrator
 * 
 */

public class Server
{
	/**
	 * java.net.ServerSocket
	   运行在服务器的Socket， 主要工作是申请服务器端口，并监听该端口
	   等待客户端的连接，一旦一个客户端与服务器建立连接，ServerSocket就会返回该客户端沟通的Socket
	 */
	
	private ServerSocket server;
	
	
	/**
	 * 构造方法，用来初始化服务端相关属性
	 */
	//原则，使用IO，网络通讯上来就写捕获异常
	public Server(){
		try{
			/**
			 * 创建ServerSocket的同时指定对外的服务
			 * 端口，客户端就是通过这个端口号连接上服务端的
			 */
			
			//现有服务端，然后才有客户端，所以先运行服务端，然后再开始客户端
			server = new ServerSocket(8088);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 服务端开始工作的方法
	 */
	public void start(){
		try{
			/**
			 * ServerSocket 的accept方法:
			 * Socket accept()
			 * 该方法用来监听服务端打开的8088端口，
			 * 等待客户端的连接，一旦一个客户端连接了就会返回
			 * 与客户端进行通信的Socket
			 * 
			 * accept是一个阻塞方法，直到一个客户端连接，否则就会一直卡在那里
			 */
			while(true){//等待客户端
				System.out.println("等待客户端连接");
				Socket socket = server.accept();
				System.out.println("一个客户端连接了");
				/**
				 * 启动一个线程，来完成与刚刚连接的客户端
				 * 的交互工作
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
	 * 该线程用来处理给定的客户端的交互作用
	 * 
	 *
	 */
	class ClientHandler implements Runnable{
		
		
		
		
		//当前线程用来交互的客户端的Socket
		private Socket socket;
		/**
		 * 该客户端的地址
		 * @param socket
		 */
		 private String host;
		public ClientHandler(Socket socket){
			/**
			 *  通过
			 */
			InetAddress address = socket.getInetAddress();
			//通常就是IP
			String host = address.getHostAddress();
			//远程计算机开启的端口
			int port = socket.getPort();
			System.out.println(host + "上线了");
			this.socket = socket;//将socket附到ClientHandler上面去了
		}
		
		public void run(){
			try{
				/**
				 * socket的方法getInputStream
				 * InputStream getInputStream
				 * 该方法用来获取一个输入流，可以读取来自远程计算机发送过来的数据
				 */
				
//				InputStream in = socket.getInputStream();
//				InputStreamReader isr = new InputStreamReader(in,"UTF-8");
//				BufferedReader br =new  BufferedReader(isr);
//				String message = null;
//				while((message = br.readLine())!= null){
//					System.out.println("客户端说" + message);
//				}
				
				
				
				
				
				InputStream  in = socket.getInputStream();
				//使用该高级流的目的在于，可以将in中读取的字节以UTF-8编码读成字符
				InputStreamReader isr = new InputStreamReader(in,"UTF-8");
				//转换成缓冲字符输入流的好处在于，可以以行为单位进行读取字符串了。
				BufferedReader br = new BufferedReader(isr);
				String message = null;
				//读取客户端发送过来的一行字符串
				/*
				 * 这里读取客户端发送过来的一行字符串的br.readLine()方法，由于客户端的操作
				 * 系统不同，导致客户端与服务器断开连接后，这个方法效果是不同的。
				 * 
				 * Linux: 当Linux 的客户端与服务器断开连接后，br.readLine(）方法会返回null
				 * windows: 当windows的客户端与服务器断开连接后，br.readLine()方法
				 *          会抛出异常
				 */
				while((message = br.readLine())!= null){
					System.out.println(host +"说：" + message);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{//不管怎样都必须执行
				System.out.println(host +"下线了");
				/**
				 * 无论是linux客户端还是windows的客户端，
				 * 当与我们断开连接后，都应该将Socket关闭，来释放资源。
				 * 
				 * 无论是socket还是流，
				 */
				try
				{
					socket.close();//将开辟的底层的东西给释放掉
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				
			}
		}
	}
}

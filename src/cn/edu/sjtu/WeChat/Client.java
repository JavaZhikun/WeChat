package cn.edu.sjtu.WeChat;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client{
	/**
	 * java.net.Socket
	 * 封装了TCP协议通讯的Socket
	 * 使用了它我们就可以与服务器的ServerSocket进行
	 * 连接，然后通过输入流和输出流与服务器进行数据交换
	 * 达到网络通讯的目的
	 */
	private Socket socket;//发起连接永远是socket去连接ServerSocket的
	/**
	 * 初始化客户端相关属性信息
	 */
	public Client(){
		try{
			/**
			 * 初始化通常传入两个参数
			 * 1.远程计算机的地址(服务器IP地址)
			 * 2.服务器打开的端口号(将外界来的程序分发给不同的应用程序，申请的端口号必须是没有被使用的，一般使用8088)
			 *  端口号的意义：
			 *  0-6535，
			 *  前1000不要用， 10000以后也很少用
			 *  端口是系统用来将外界数据转发给某个应用程序的，每个程序
			 *  若想访问网络都需要申请一个对外的端口，这样当外界通过Ip地址
			 *  找到我们的计算机后，通过该端口就可以将数据交给我们的程序来处理了。
			 *  
			 *  这里写的8088并不是我们客户端这边对外的端口，因为我们要连接服务器，
			 *  所以这个是服务器那边的程序申请的对外端口，我们可以通过该
			 *  端口连接服务器的程序
			 *  
			 *  new 的过程就是连接的过程，就会根据给定的地址与端口
			 *  与远程计算机发起连接， 成功的话会返回Socket实例，否则会抛出异常。
			 *  
			 */
			socket = new Socket("192.168.30.77", 8088);//localhost相当于连接本机
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 客户端开始工作的方法
	 */
	public void start(){
		try{
			/**
			 * Socket的getOutputStream方法，可以获取一个字节输出流
			 * 通过该流
			 * 会发送至远端
			 */
			
			OutputStream out = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");//将字节流转换为utf-8的字符流
			PrintWriter pw = new PrintWriter(osw,true);//使用方法print,可以直接写一行,
			//后面是自动行刷新就能保证没写一句话都可以自动flush
			//while(true){
				pw.println("你好！服务器！");
				
			//}
			Scanner scanner = new Scanner(System.in);
			while(true){
				System.out.println("请输入");
				String message = scanner.nextLine();
				pw.println("客户端" + message);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void main(String[] args)
	{
		Client client = new Client();
		client.start();
	}
	
	
	

}

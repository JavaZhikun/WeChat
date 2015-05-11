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
	 * ��װ��TCPЭ��ͨѶ��Socket
	 * ʹ���������ǾͿ������������ServerSocket����
	 * ���ӣ�Ȼ��ͨ�����������������������������ݽ���
	 * �ﵽ����ͨѶ��Ŀ��
	 */
	private Socket socket;//����������Զ��socketȥ����ServerSocket��
	/**
	 * ��ʼ���ͻ������������Ϣ
	 */
	public Client(){
		try{
			/**
			 * ��ʼ��ͨ��������������
			 * 1.Զ�̼�����ĵ�ַ(������IP��ַ)
			 * 2.�������򿪵Ķ˿ں�(��������ĳ���ַ�����ͬ��Ӧ�ó�������Ķ˿ںű�����û�б�ʹ�õģ�һ��ʹ��8088)
			 *  �˿ںŵ����壺
			 *  0-6535��
			 *  ǰ1000��Ҫ�ã� 10000�Ժ�Ҳ������
			 *  �˿���ϵͳ�������������ת����ĳ��Ӧ�ó���ģ�ÿ������
			 *  ����������綼��Ҫ����һ������Ķ˿ڣ����������ͨ��Ip��ַ
			 *  �ҵ����ǵļ������ͨ���ö˿ھͿ��Խ����ݽ������ǵĳ����������ˡ�
			 *  
			 *  ����д��8088���������ǿͻ�����߶���Ķ˿ڣ���Ϊ����Ҫ���ӷ�������
			 *  ��������Ƿ������Ǳߵĳ�������Ķ���˿ڣ����ǿ���ͨ����
			 *  �˿����ӷ������ĳ���
			 *  
			 *  new �Ĺ��̾������ӵĹ��̣��ͻ���ݸ����ĵ�ַ��˿�
			 *  ��Զ�̼�����������ӣ� �ɹ��Ļ��᷵��Socketʵ����������׳��쳣��
			 *  
			 */
			socket = new Socket("192.168.30.77", 8088);//localhost�൱�����ӱ���
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * �ͻ��˿�ʼ�����ķ���
	 */
	public void start(){
		try{
			/**
			 * Socket��getOutputStream���������Ի�ȡһ���ֽ������
			 * ͨ������
			 * �ᷢ����Զ��
			 */
			
			OutputStream out = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");//���ֽ���ת��Ϊutf-8���ַ���
			PrintWriter pw = new PrintWriter(osw,true);//ʹ�÷���print,����ֱ��дһ��,
			//�������Զ���ˢ�¾��ܱ�֤ûдһ�仰�������Զ�flush
			//while(true){
				pw.println("��ã���������");
				
			//}
			Scanner scanner = new Scanner(System.in);
			while(true){
				System.out.println("������");
				String message = scanner.nextLine();
				pw.println("�ͻ���" + message);
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

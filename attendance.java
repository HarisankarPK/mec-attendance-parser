//code by MANS1AY3R & RAKNASIRAH
import java.io.*;
import java.net.*;
import java.util.*;
class attendance
{ 
   public static void net_part(String clss)     //Retrieves data from attnd website
   {
    try
   {
    String cl="class="+URLEncoder.encode(clss,"UTF-8")+"&"+"Submit="+URLEncoder.encode("View ","UTF-8");
    URL url=new URL("http://mec.ac.in/attn/view4stud.php");
	HttpURLConnection conn = (HttpURLConnection)url.openConnection();
	conn.setDoOutput(true);
    OutputStream out =conn.getOutputStream();
    
    out.write(cl.getBytes());
   
	PrintWriter newout=new PrintWriter(new File("newdata.html"));
	
    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) 
	{
	  System.out.println("All ok");
	  BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	  
        String decodedString;
	 while ((decodedString = in.readLine()) != null) 
            newout.println(decodedString);
	}
	else
	{
	  System.out.println("Somethings wrong");
	}
	newout.close();
	conn.disconnect();
	}
	catch(Exception e)
	{
	  e.printStackTrace();
	}   
   }
   public static void processing()   //Removes the tags from html page (WIP)
   {
     try
	 {
     BufferedReader fin=new BufferedReader(new FileReader("newdata.html"));
	 char ch='a';
	 BufferedWriter fout=new BufferedWriter(new FileWriter("newnewdata.txt"));
	 while(ch<256)
	 {
	    ch=(char) fin.read();
		while(ch=='<')
		   while(ch!='>')
		     ch=(char) fin.read();
	    if(ch!='>'&&ch!=' ')
	    fout.write(ch);
	 }
	 fin.close();
	 fout.flush();
	 fout.close();
     }
	 catch(Exception e)
	 {
	    System.out.println("Somethings wrong");
	 }
   }


public static void nameget(String name)
{
    try
    {
	String textcont= new Scanner(new File("newdata.html")).useDelimiter("\\Z").next();
	if (textcont.contains(name))
	{
		int i;
		int loc=textcont.indexOf(name);
		String[] nameSplit=textcont.split(name);
		String[] attnSnip=nameSplit[1].split("</tr>");
		String[] rawNums=attnSnip[0].split("[^0-9].[^0-9]+");
		String[] subSnip=textcont.split("</tr></table><br>");
		String[] subRaw=subSnip[1].split("Spl.");
		String[] subsCutoff=subRaw[0].split("<tr>");	
		String[][] subs=new String[subsCutoff.length][];	
		for(i=0;i<subs.length;i++)
		{
			subs[i]=subsCutoff[i].split("&nbsp;&nbsp;&nbsp;");
		}

		System.out.print("Generating Attendance report for "+name+"\n\n\n");
		for(i=0;i<rawNums.length-3;i++)
		{
			System.out.println("\t"+rawNums[i+1]+"%\tin\t"+subs[i+1][0]);
		}
		System.out.println("\t"+rawNums[++i]+"\tdays in\tSpecial Attendance");
		System.out.println("\n\tWhich gives an average of "+rawNums[++i]+"% attendance\n\n");
		
	}
	else 
	{
		System.out.println("aye, we dont have no " + name + " in this class");
    		throw new IllegalArgumentException("aye, we dont have no " + name + " in this class");
	}
	
    }
	catch(Exception e)
	{
	    System.out.println("Aye, something is not doing matey");
	}	
}

 public static void main(String args[])
 {
    System.out.println("\n\nMEC ATTENDANCE JAVA APPLICATION by Jojo Joseph and Harisankar P K\n\n");
    if(args.length<2)
	 {  
	    System.out.println("Usage: Give name and class as parameters. Eg: attendance \"BRUCE LEE\" C4B\n");
	    System.exit(0);
      }	  
    net_part(args[1]);
    processing();

    nameget(args[0]); 
 }

}

package com.young.string;

import java.awt.Container;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

public class AndroidStringBat implements ActionListener {

	static String tempName = "/home/yangxijun/桌面/temp.xml";

	static String translatedStringPath = null;
	static String saveTranslatedStringPath = null;

	static int translateCount = 0;

	JFrame frame = new JFrame("多语言翻译");// 框架布局
	JTabbedPane tabPane = new JTabbedPane();// 选项卡布局
	Container con = new Container();//
	JLabel label2 = new JLabel("多语言路径");
	JLabel label3 = new JLabel("保存的路径(res目录)");
	JTextField text2 = new JTextField();// 文件的路径
	JTextField text3 = new JTextField();// 文件的路径
	JButton button2 = new JButton("...");// 选择
	JButton button3 = new JButton("...");// 选择
	JFileChooser jfc = new JFileChooser();// 文件选择器
	JButton button4 = new JButton("翻译");//

	public AndroidStringBat() {
		initview();
	}

	public void initview() {
		// jfc.setCurrentDirectory(new File(saveTranslatedStringPath));//
		// 文件选择器的初始目录定为d盘
		double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		frame.setLocation(new Point((int) (lx / 2) - 150, (int) (ly / 2) - 150));// 设定窗口出现位置
		frame.setSize(400, 200);// 设定窗口大小
		frame.setContentPane(tabPane);// 设置布局
		label2.setBounds(10, 35, 150, 20);
		label3.setBounds(10, 60, 150, 20);

		text2.setBounds(155, 35, 120, 20);
		text3.setBounds(155, 60, 120, 20);
		button2.setBounds(280, 35, 50, 20);
		button3.setBounds(280, 60, 50, 20);
		button4.setBounds(10, 85, 60, 20);

		button2.addActionListener(this); // 添加事件处理
		button4.addActionListener(this); // 添加事件处理
		button3.addActionListener(this); // 添加事件处理
		con.add(label2);
		con.add(label3);
		con.add(text2);
		con.add(text3);
		con.add(button2);
		con.add(button4);
		con.add(button3);
		frame.setVisible(true);// 窗口可见
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 使能关闭窗口，结束程序
		tabPane.add("翻译软件", con);// 添加布局1
	}

	public static void main(String[] args) {
		new AndroidStringBat();
	}

	/**
	 * 在文件里面的指定行插入一行数据
	 * 
	 * @param inFile
	 *            文件
	 * @param lineno
	 *            行号
	 * @param str
	 *            要插入的数据
	 * @throws Exception
	 *             IO操作引发的异常
	 */
	public static void insertStringInFile(File inFile, int lineStart, ArrayList<String> list) throws Exception {
		File outFile = new File(tempName);
		FileInputStream fis = new FileInputStream(inFile);
		FileInputStream fisForCount = new FileInputStream(inFile);
		BufferedReader in = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		BufferedReader inForCount = new BufferedReader(new InputStreamReader(fisForCount, "UTF-8"));
		FileOutputStream fos = new FileOutputStream(outFile);
		PrintWriter out = new PrintWriter(fos);
		int lineCount = 1;
		String line;
		while ((line = inForCount.readLine()) != null) {
			lineCount++;
		}

		// 行号从1开始
		int i = 1;
		String thisLine;
		int size = list.size() - 1;
		while ((thisLine = in.readLine()) != null) {
			// 如果行号再范围内，则输出要插入的数据
			if (i >= lineStart && i < lineStart + size) {
				int num = 1 + i - lineStart;
				String key = handleString(list.get(num));
				String str = "<string name=\"startpage_new_function_" + num + "\">" + key + "</string>";
				out.println(str);
			} else if (i != lineCount - 1) {
				out.println(thisLine);
			} else if (i == lineCount - 1) {
				out.print(thisLine);
			}
			i++;
		}

		out.flush();
		out.close();
		in.close();

		inFile.delete();
		outFile.renameTo(inFile);

		translateCount++;
	}

	// 对string进行基本处理
	private static String handleString(String str) {
		String res;
		res = str.trim();
		res = res.replace("\'", "\\\'");
		res = res.replace("&", "&amp;");
		return res;
	}


	/**
	 * 时间监听的方法
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(button2)) {
			jfc.setCurrentDirectory(new File("/home/yangxijun/桌面/"));
			jfc.setFileSelectionMode(0);// 设定只能选择到文件
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				text2.setText(f.getAbsolutePath());
				translatedStringPath = f.getAbsolutePath();
			}
		}
		if (e.getSource().equals(button3)) {
			jfc.setCurrentDirectory(new File("/home/yangxijun/3g/MyWorkspace/"));
			jfc.setFileSelectionMode(1);// 设定只能选择到文件
			int state = jfc.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
			if (state == 1) {
				return;// 撤销则返回
			} else {
				File f = jfc.getSelectedFile();// f为选择到的文件
				text3.setText(f.getAbsolutePath());
				saveTranslatedStringPath = f.getAbsolutePath();
			}
		}
		if (e.getSource().equals(button4)) {
			translateCount = 0;
			valuesPathMapLanguage();
			JOptionPane.showMessageDialog(null, "翻译完成！匹配到语言数为:" + translateCount + " ,总数应为:15!", "提示", 2);
		}
	}

	public ArrayList<String> getSavePath() {
		if (saveTranslatedStringPath == null) {
			return null;
		}
		File file = new File(saveTranslatedStringPath);
		String[] paths = file.list();
		ArrayList<String> valuesPaths = new ArrayList<String>();
		for (int i = 0; i < paths.length; i++) {
			if (paths[i].startsWith("values")) {
				valuesPaths.add(paths[i]);
			}
		}
		return valuesPaths;
	}

	public void valuesPathMapLanguage() { // excel表中语言对应valuse文件夹
		Sheet mSheet = getExcel();
		for (int i = 0; i < mSheet.getColumns(); i++) {
			ArrayList<String> mLanguage = new ArrayList<String>();
			for (int j = 0; j < mSheet.getRows(); j++) {
				Cell cell = mSheet.getCell(i, j);
				mLanguage.add(cell.getContents());
			}

			String lan = mLanguage.get(0).toString();
			lan = lan.toLowerCase();
			if (lan.equals("en")) {
				String savePath = saveTranslatedStringPath + "/values/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("de")) {
				String savePath = saveTranslatedStringPath + "/values-de/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("es")) {
				String savePath = saveTranslatedStringPath + "/values-es/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("fr")) {
				String savePath = saveTranslatedStringPath + "/values-fr/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("hi")) {
				String savePath = saveTranslatedStringPath + "/values-hi/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("in")) {
				String savePath = saveTranslatedStringPath + "/values-in/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("it")) {
				String savePath = saveTranslatedStringPath + "/values-it/strings.xml";
				mLanguage.set(0, savePath);
			}

			// ja也加进来
			if (lan.equals("jp")) {
				String savePath = saveTranslatedStringPath + "/values-ja/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("ko")) {
				String savePath = saveTranslatedStringPath + "/values-ko/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("pt")) {
				String savePath = saveTranslatedStringPath + "/values-pt/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("ro")) {
				String savePath = saveTranslatedStringPath + "/values-ro/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("ru")) {
				String savePath = saveTranslatedStringPath + "/values-ru/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("th")) {
				String savePath = saveTranslatedStringPath + "/values-th/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("tr")) {
				String savePath = saveTranslatedStringPath + "/values-tr/strings.xml";
				mLanguage.set(0, savePath);
			}
			if (lan.equals("zh_hk")) {
				String savePath = saveTranslatedStringPath + "/values-zh/strings.xml";
				mLanguage.set(0, savePath);
			}

			String filename = mLanguage.get(0).toString();

			try {
				insertStringInFile(new File(filename), mLanguage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static final String NEW_FUNC_1 = "startpage_new_function_1";

	public void insertStringInFile(File file, ArrayList<String> list) throws Exception {
		if (!file.exists()) {
			return;
		}
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		// 从1开始
		int line = 1;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			String key = NEW_FUNC_1;
			if (lineTxt.contains(key)) {
				insertStringInFile(file, line, list);
				break;
			}
			line++;
		}
	}

	public Sheet getExcel() {
		Sheet sheet = null;
		try {
			if (translatedStringPath == null) {
				return null;
			}
			WorkbookSettings settings = new WorkbookSettings();
			settings.setEncoding("ISO-8859-1");
			Workbook wb = Workbook.getWorkbook(new File(translatedStringPath), settings); // 从文件流中获取Excel工作区对象（WorkBook）
			sheet = wb.getSheet(0); // 从工作区中取得页（Sheet）
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheet;
	}
}

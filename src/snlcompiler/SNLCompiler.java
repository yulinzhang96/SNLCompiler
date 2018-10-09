/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snlcompiler;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import static snlcompiler.SNLCompiler.displayJTA;

public class SNLCompiler extends JFrame {

    private Container container;
    private GridLayout grid1;
    JPanel northJP = new JPanel(), centerJP = new JPanel(), southJP = new JPanel();// 此对话框采用边界布局，根据中、南部而分别创建中、南面板
    JLabel label1 = new JLabel("源程序");
    JLabel label2 = new JLabel("词法分析");
    JLabel label3 = new JLabel("LL(1)语法分析");
    JLabel label4 = new JLabel("LL(1)语法树");
    JScrollPane scrollJSP = new JScrollPane();
    JScrollPane scrollJSP2 = new JScrollPane();
    JScrollPane scrollJSP3 = new JScrollPane();
    JScrollPane scrollJSP4 = new JScrollPane();
    static JTextArea displayJTA = new JTextArea();
    static JTextArea displayJTA2 = new JTextArea();
    static JTextArea displayJTA3 = new JTextArea();
    static JTextArea displayJTA4 = new JTextArea();
    JButton analyzeTokenJB = new JButton("词法分析");
    JButton analyzeGrammarJB = new JButton("LL(1)语法分析");
    JButton analyzeGrammarJB2 = new JButton("LL(1)语法树");
    JButton helpJB = new JButton("使用说明");

    public SNLCompiler() {
        setTitle("SNL编译器");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int windowWidth = (int) (1.0 * screenSize.width), windowHeight = (int) (0.85 * screenSize.height);
        setBounds((int) ((screenSize.width - windowWidth) * 0.5),
                (int) ((screenSize.height - windowHeight) * 0.5), windowWidth,
                windowHeight);
        setLayout(new BorderLayout());
        add(northJP, BorderLayout.NORTH);
        add(centerJP, BorderLayout.CENTER);
        add(southJP, BorderLayout.SOUTH);
        northJP.setLayout(new GridLayout(1, 4, 2, 2));
        centerJP.setLayout(new GridLayout(1, 4, 2, 2));
        southJP.setLayout(new GridLayout(1, 4, 2, 2));

        southJP.add(analyzeTokenJB);
        southJP.add(analyzeGrammarJB);
        southJP.add(analyzeGrammarJB2);
        southJP.add(helpJB);

        centerJP.add(scrollJSP);
        scrollJSP.setViewportView(displayJTA);
        displayJTA.setFont(new Font("Times New Roman", Font.BOLD, 14));
        centerJP.add(scrollJSP2);
        scrollJSP2.setViewportView(displayJTA2);
        displayJTA2.setFont(new Font("宋体", Font.BOLD, 14));
        centerJP.add(scrollJSP3);
        scrollJSP3.setViewportView(displayJTA3);
        displayJTA3.setFont(new Font("宋体", Font.BOLD, 14));
        centerJP.add(scrollJSP4);
        scrollJSP4.setViewportView(displayJTA4);
        displayJTA4.setFont(new Font("宋体", Font.BOLD, 14));

        northJP.add(label1);
        northJP.add(label2);
        northJP.add(label3);
        northJP.add(label4);

        createToken.initialize();

        analyzeTokenJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if (analyzeToken.analyzeToken(displayJTA.getText())) {
                    analyzeGrammarJB.setEnabled(true);
                } else {
                    analyzeGrammarJB.setEnabled(false);
                    analyzeGrammarJB2.setEnabled(false);
                }
                displayJTA2.setText(createToken.tokenDisplay.toString());
            }
        });
        analyzeGrammar LL1Grammar = new analyzeGrammar();
        analyzeGrammarJB.setEnabled(false);// 设置LL(1)语法分析按钮初始时不可用
        analyzeGrammarJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayJTA3.setText(LL1Grammar.analyzeGrammar());
                if (LL1Grammar.isSuccess) {
                    analyzeGrammarJB2.setEnabled(true);
                }
            }
        });
        analyzeGrammarJB2.setEnabled(false);// 设置LL(1)语法树输出按钮初始时不可用
        analyzeGrammarJB2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                displayJTA4.setText(LL1Grammar.printGrammarTree());
            }
        });
        helpJB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                String s = "1.在进行词法分析时，输入为源程序，输出为Token序列\n"
                        + "    Token序列的格式为: line(行数),LEX(词法信息),SEM(语义信息)\n"
                        + "2.在进行语法分析时，用户可以选择使用LL1(1)语法分析方法和递归下降分析方法"
                        + "   语法分析的输入为词法分析后的Token序列\n"
                        + "   语法分析输出格式为: 节点在语法树上的层数: 节点信息\n"
                        + "3.若词法分析失败，则语法分析功能将不可用，直到词法分析成功\n"
                        + "4.若语法分析失败，则语法树无法输出，该功能不可用\n"
                        + "5.语法分析时直接将所有标识符统一表示为ID，将所有数字常量统一表示为INTC\n";
                JOptionPane.showMessageDialog(SNLCompiler.this, s, "使用说明", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new SNLCompiler();
    }
}

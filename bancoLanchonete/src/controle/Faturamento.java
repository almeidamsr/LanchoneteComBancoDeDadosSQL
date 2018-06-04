package controle;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JPasswordField;
import javax.swing.JTextField;

import banco.BancoDeDados;

public class Faturamento {

	JFrame janela;
	JPanel cabecalho;
	JPanel painelDoFaturamento;
	JPanel rodape;
	//ImageIcon figura = new ImageIcon("FaturamentoMA.png");
	JButton botaoFaturamento = new JButton("                CONFIRMAR             ");
	
	Calendar cal = Calendar.getInstance();
	SimpleDateFormat formatData = new SimpleDateFormat("YYYY-MM-dd");
	String dataAtual = formatData.format(cal.getTime());
	
	public JLabel txtDataInicial = new JLabel("Data Inicial: ", JLabel.LEFT);
	public JTextField campoDataInicial = new JTextField(dataAtual);
	public JLabel txtDataFinal = new JLabel("Data Final: ", JLabel.LEFT);;
	public JTextField campoDataFinal = new JTextField(dataAtual);
	public String dataInicial;
	public String dataFinal;

	BancoDeDados bancoDeDados = new BancoDeDados();

	public int retornoCodPedidoVenda = 1;
	
	public void CriarJanelaFaturamento() {

		Pedido pedido = new Pedido();
		
		cabecalho = new JPanel();
		cabecalho.setBackground(Color.orange);
		cabecalho.setBorder(new javax.swing.border.LineBorder(Color.LIGHT_GRAY, 4, true));
		cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.X_AXIS));
		cabecalho.add(Box.createHorizontalStrut(200));
		cabecalho.add(new JLabel("Faturamento Por Mês"));
		cabecalho.add(Box.createVerticalStrut(50));
	//	cabecalho.add(new JLabel(figura), BorderLayout.CENTER);
		

		painelDoFaturamento = new JPanel();
		painelDoFaturamento.setLayout(new BoxLayout(painelDoFaturamento, BoxLayout.Y_AXIS));
		painelDoFaturamento.add(Box.createVerticalStrut(70));
	  //painelDoFaturamento.setBackground(Color.LIGHT_GRAY);
		painelDoFaturamento.setLayout(new GridLayout(10, 3));
		painelDoFaturamento.add(Box.createVerticalBox());		
		painelDoFaturamento.add(Box.createVerticalStrut(20));
		painelDoFaturamento.add(txtDataInicial);
		painelDoFaturamento.add(campoDataInicial);
		painelDoFaturamento.add(txtDataFinal);
		painelDoFaturamento.add(campoDataFinal);
		painelDoFaturamento.add(Box.createVerticalStrut(20));
		painelDoFaturamento.add(botaoFaturamento);

		// Executa o botao registrar Faturamento.
		botaoFaturamento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				dataInicial = campoDataInicial.getText();
				dataFinal = campoDataFinal.getText();
				JOptionPane.showMessageDialog(null, "Faturamento do dia "+dataInicial+" Até o dia "+ dataFinal+" = R$: 500,00");
			}

		});

		janela = new JFrame("Faturamento Por Mês");
		janela.setSize(500, 300);
		janela.setLocation(450, 240);
		janela.setLayout(new BorderLayout());
		janela.add(cabecalho, BorderLayout.NORTH);
		janela.add(painelDoFaturamento, BorderLayout.CENTER);
		janela.add(Box.createHorizontalStrut(120), BorderLayout.EAST);
		janela.add(Box.createHorizontalStrut(120), BorderLayout.WEST);
		janela.add(new JTextField("PROG 4"), BorderLayout.SOUTH);
	}

	public void exibirJanelaFaturamento() {
		CriarJanelaFaturamento();
		janela.setLocation(300, 200);
		//janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setVisible(true);
	}
	public void fecharJanelaFaturamento(){
		janela.dispose();
	}

}

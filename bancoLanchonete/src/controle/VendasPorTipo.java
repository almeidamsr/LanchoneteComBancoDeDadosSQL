package controle;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//import javax.swing.JPasswordField;
import javax.swing.JTextField;

import banco.BancoDeDados;

public class VendasPorTipo extends BancoDeDados{

	JFrame janela;
	JPanel cabecalho;
	JPanel painelDeVendasPorTipo;
	JPanel rodape;
    JLabel txtComboTipo= new JLabel("Tipo de Produto: ", JLabel.LEFT);
	JButton botaoVendasPorTipo = new JButton("                CONFIRMAR             ");
	JComboBox comboTipoProduto;
	public String tipoProduto;
	public ArrayList<String> arrayTipo = new ArrayList<>();

	BancoDeDados bancoDeDados = new BancoDeDados();
	
	public void CriarJanelaVendasPorTipo() {

		Pedido pedido = new Pedido();
		
		cabecalho = new JPanel();
		cabecalho.setBackground(Color.orange);
		cabecalho.setBorder(new javax.swing.border.LineBorder(Color.LIGHT_GRAY, 4, true));
		cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.X_AXIS));
		cabecalho.add(Box.createHorizontalStrut(200));
		cabecalho.add(new JLabel("Vendas Por Tipo de Produto"));
		cabecalho.add(Box.createVerticalStrut(50));
	//	cabecalho.add(new JLabel(figura), BorderLayout.CENTER);
		
		// String[] options = { "Option1", "Option2", "Option3", "Option4", "Option15" };
		// comboTipoProduto = new JComboBox(options);
		 
		 guardarTipoProduto();
		 comboTipoProduto = new JComboBox(arrayTipo.toArray());
		 comboTipoProduto.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                // Do something when you select a value

	            }
	        });
		
		
		painelDeVendasPorTipo = new JPanel();
		painelDeVendasPorTipo.setLayout(new BoxLayout(painelDeVendasPorTipo, BoxLayout.Y_AXIS));
		painelDeVendasPorTipo.add(Box.createVerticalStrut(70));
	  //painelDeVendasPorTipo.setBackground(Color.LIGHT_GRAY);
		painelDeVendasPorTipo.setLayout(new GridLayout(10, 3));
		painelDeVendasPorTipo.add(Box.createVerticalBox());
		painelDeVendasPorTipo.add(txtComboTipo);
		painelDeVendasPorTipo.add(comboTipoProduto);
		painelDeVendasPorTipo.add(Box.createVerticalStrut(20));
		painelDeVendasPorTipo.add(botaoVendasPorTipo);

		// Executa o botao registrar VendasPorTipo.
		botaoVendasPorTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				comboTipoProduto.getSelectedItem();
				tipoProduto = comboTipoProduto.getSelectedItem()+"";
				System.out.println(tipoProduto);
				
				JOptionPane.showMessageDialog(null, "QUATIDADE DE "+tipoProduto+"(s) VENDIDOS(AS) = 100");
			}

		});

		janela = new JFrame("Vendas Por Tipo");
		janela.setSize(500, 300);
		janela.setLocation(450, 240);
		janela.setLayout(new BorderLayout());
		janela.add(cabecalho, BorderLayout.NORTH);
		janela.add(painelDeVendasPorTipo, BorderLayout.CENTER);
		janela.add(Box.createHorizontalStrut(120), BorderLayout.EAST);
		janela.add(Box.createHorizontalStrut(120), BorderLayout.WEST);
		janela.add(new JTextField("PROG 4"), BorderLayout.SOUTH);
	}

	public void exibirJanelaVendasPorTipo() {
		CriarJanelaVendasPorTipo();
		janela.setLocation(300, 200);
		//janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setVisible(true);
	}
	public void fecharJanelaVendasPorTipo(){
		janela.dispose();
	}
	
	public void guardarTipoProduto() {
		try {
			conectar();
			String query = "select tipoProduto from lanchonete_produto;";
			this.resultado = this.comando.executeQuery(query);
			while (this.resultado.next()) {
				arrayTipo.add(this.resultado.getString("tipoProduto"));
			}
		} catch (Exception e) {
			System.out.println("guardarTipoProduto Erro : " + e.getMessage());
		}
	}

}

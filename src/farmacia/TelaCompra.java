package farmacia;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class TelaCompra extends Composite {
	private DateTime dateTime;
	private Text textDosagem;
	private Text textQuant;
	private Combo comboCli;
	private Combo comboMed;
	private Label lblQuantTotal;
	
	private Device device = Display.getCurrent();
	private Color red = new Color(device, 255,0,0);
	private Color white;
	
	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	private ArrayList<Medicamento> medicamentos = new ArrayList<Medicamento>();
	private Label lblDosagemInfo;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TelaCompra(Composite parent, int style) {
		super(parent, style);
		
		dateTime = new DateTime(this, SWT.BORDER);
		dateTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		dateTime.setBounds(132, 10, 200, 54);
		
		Label lblData = new Label(this, SWT.NONE);
		lblData.setBounds(10, 26, 116, 19);
		lblData.setText("Data da Compra");
		
		comboCli = new Combo(this, SWT.NONE);
		comboCli.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				atualizaLabels();
			}
		});
		comboCli.setBounds(132, 70, 193, 35);
		
		Label lblCliente = new Label(this, SWT.NONE);
		lblCliente.setBounds(10, 78, 116, 27);
		lblCliente.setText("Cliente");
		
		Label lblMedicamento = new Label(this, SWT.NONE);
		lblMedicamento.setText("Medicamento");
		lblMedicamento.setBounds(10, 119, 116, 27);
		
		comboMed = new Combo(this, SWT.NONE);
		comboMed.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				atualizaLabels();
			}
		});
		comboMed.setBounds(132, 111, 193, 35);
		
		textDosagem = new Text(this, SWT.BORDER);
		textDosagem.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				atualizaLabels();
			}
		});
		textDosagem.setBounds(132, 152, 97, 23);
		white = textDosagem.getBackground();
		
		Label lblDosagem = new Label(this, SWT.NONE);
		lblDosagem.setBounds(10, 156, 72, 19);
		lblDosagem.setText("Dosagem");
		
		Label lblQuantidade = new Label(this, SWT.NONE);
		lblQuantidade.setText("Quantidade");
		lblQuantidade.setBounds(10, 185, 116, 19);
		
		textQuant = new Text(this, SWT.BORDER);
		textQuant.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				atualizaLabels();
			}
		});
		textQuant.setBounds(132, 181, 97, 23);
		
		lblQuantTotal = new Label(this, SWT.NONE);
		lblQuantTotal.setBounds(235, 185, 205, 19);
		
		Button btnComprar = new Button(this, SWT.NONE);
		btnComprar.setBounds(181, 238, 94, 33);
		btnComprar.setText("Comprar");
		btnComprar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					getTela().cadastrar();
				} catch (NullPointerException ef) {
					ef.printStackTrace();
				}
			}
		});
		
		
		lblDosagemInfo = new Label(this, SWT.NONE);
		lblDosagemInfo.setBounds(235, 156, 205, 19);
		
		preencheClientes();
		preencheMedicamentos();
	}

	private void preencheClientes() {
		comboCli.setItems(new String[]{});
		clientes = Cliente.listar();
		for (Cliente c : clientes) {
			comboCli.add(c.getNome());
		}
	}
	
	private void preencheMedicamentos() {
		comboMed.setItems(new String[]{});
		medicamentos = Medicamento.listar(null);
		for (Medicamento m : medicamentos) {
			comboMed.add(m.getNome());
		}
	}
	
	private void atualizaLabels() {
		Compra compraTemp = getTela();
		if(compraTemp != null) {
			String unidade = compraTemp.getMedicamento().getApresentacao();
			if (unidade.equals("liquido"))
				unidade = "ml";
			lblQuantTotal.setText(compraTemp.getQtAdquirida() + " " + unidade);
			lblDosagemInfo.setText(unidade + " por dia");
		}
	}
	
	private Compra getTela() {
			comboCli.setBackground(white);
			comboMed.setBackground(white);
			textQuant.setBackground(white);
			textDosagem.setBackground(white);
			Compra c = new Compra();
			Date dataCompra = new Date(dateTime.getYear()-1900,dateTime.getMonth(),dateTime.getDay());
			c.setData(dataCompra);
			try {
				c.setCliente(clientes.get(comboCli.getSelectionIndex()));
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				comboCli.setBackground(red);
				return null;
			}
			
			try {
				c.setMedicamento(medicamentos.get(comboMed.getSelectionIndex()));
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				comboMed.setBackground(white);
				return null;
			}
			
			int quant, dosagem;
			try {
				quant = Integer.parseInt(textQuant.getText());
				if(quant <= 0)
					throw new Exception();
			} catch (Exception nfe) {
				textQuant.setBackground(red);
				return null;
			}
			try {
				dosagem = Integer.parseInt(textDosagem.getText());
				if(dosagem <= 0)
					throw new Exception();
			} catch (Exception nfe) {
				textDosagem.setBackground(red);
				return null;
			}
			c.setDosagem(dosagem);
			c.setQtAdquirida(quant * c.getMedicamento().getQuantidade());
			return c;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

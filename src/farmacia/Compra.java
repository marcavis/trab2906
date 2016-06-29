package farmacia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Compra {
	private Date data;
	private int dosagem;
	private int qtAdquirida;
	private Cliente cliente;
	private Medicamento medicamento;
	
	public void cadastrar() {
		String sql = "insert into compra (data, dosagem, qtAdquirida, cliente, medicamento) values (?,?,?,?,?)";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setDate(1, new java.sql.Date(getData().getTime()));
			ps.setInt(2, getDosagem());
			ps.setInt(3, getQtAdquirida());
			ps.setInt(4, cliente.getId());
			ps.setInt(5, medicamento.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public void alterar() {
//		String sql = "update compra set data=?, dosagem=?, qt_adquirida=?, cliente=?, medicamento=?"; //where id=?";
//		try {
//			PreparedStatement ps = Principal.conn.prepareStatement(sql);
//			ps.setDate(1, new java.sql.Date(getData().getTime()));
//			ps.setInt(2, getDosagem());
//			ps.setInt(3, getQtAdquirida());
//			ps.setInt(4, cliente.getId());
//			ps.setInt(5, medicamento.getId());
//			
//			ps.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	public void excluir() {
//		String sql = "delete from paciente where id=?";
//		try {
//			PreparedStatement ps = Principal.conn.prepareStatement(sql);
//			ps.setInt(1, getId());
//			ps.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
//	public static ArrayList<Compra> listar(String filtro) {
//		ArrayList<Compra> lista = new ArrayList<Compra>();
//		String sql = "select * from paciente order by nome";
//		//if(filtro != null) {
//		//	sql = "select * from paciente where nome like ? order by nome";
//		//}
//		try {
//			PreparedStatement ps = Principal.conn.prepareStatement(sql);
//			
//			if(filtro != null) {
//				ps.setString(1, filtro);
//			}
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				Compra c = new Compra();
//				c.setId(rs.getInt("id"));
//				c.setNome(rs.getString("nome"));
//				c.setNascimento(rs.getDate("data_nasc"));
//				c.setSexo(rs.getString("sexo"));
//				lista.add(c);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return lista;
//	}
	
//	public Compra buscaPorData(Date data) {
//		Compra c = new Compra();
//		String sql = "select * from paciente where data=?";
//		try {
//			PreparedStatement ps = Principal.conn.prepareStatement(sql);
//			ps.setInt(1, id);
//			ResultSet rs = ps.executeQuery();
//			if(rs.next()) {
//				c.setId(rs.getInt("id"));
//				c.setNome(rs.getString("nome"));
//				c.setNascimento(rs.getDate("data_nasc"));
//				c.setSexo(rs.getString("sexo"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return c;
//	}
	
//	public static ArrayList<Compra> listaPendentesPorData(Date data) {
//		ArrayList<Compra> lista = new ArrayList<Compra>();
//		String sql = "select * from compra where data=?";
//		try {
//			PreparedStatement ps = Principal.conn.prepareStatement(sql);
//			
//			ps.setDate(1, new java.sql.Date(data.getTime()));
//			
//			ResultSet rs = ps.executeQuery();
//			while(rs.next()) {
//				Compra c = new Compra();
//				c.setData(rs.getDate("data"));
//				c.setDosagem(rs.getInt("dosagem"));
//				c.setQtAdquirida(rs.getInt("qtadquirida"));
//				c.setCliente(new Cliente().buscaPorID(rs.getInt("cliente")));
//				c.setMedicamento(new Medicamento().buscaPorID(rs.getInt("medicamento")));
//				lista.add(c);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return lista;
//	}
	
	public static ArrayList<Compra> listarPorCliente(Cliente cliente) {
	ArrayList<Compra> lista = new ArrayList<Compra>();
	String sql = "select * from compra where cliente=?";
	try {
		PreparedStatement ps = Principal.conn.prepareStatement(sql);
		
		ps.setInt(1, cliente.getId());
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			Compra c = new Compra();
			c.setData(rs.getDate("data"));
			c.setDosagem(rs.getInt("dosagem"));
			c.setQtAdquirida(rs.getInt("qtadquirida"));
			c.setCliente(cliente);
			c.setMedicamento(new Medicamento().buscaPorID(rs.getInt("medicamento")));
			lista.add(c);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return lista;
}


	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getDosagem() {
		return dosagem;
	}

	public void setDosagem(int dosagem) {
		this.dosagem = dosagem;
	}

	public int getQtAdquirida() {
		return qtAdquirida;
	}

	public void setQtAdquirida(int qtAdquirida) {
		this.qtAdquirida = qtAdquirida;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Medicamento getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}

	public String[] toArray() {
		return new String[]{getData().toString(), getDosagem()+"", getQtAdquirida()+""};
	}

	
}

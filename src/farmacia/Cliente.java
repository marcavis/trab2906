package farmacia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class Cliente {
	private int id;
	private String nome;
	private Date nascimento;
	private String sexo; 
	
	public void cadastrar() {
		String sql = "insert into paciente (nome, data_nasc, sexo) values (?,?,?)";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setDate(2, new java.sql.Date(getNascimento().getTime()));
			ps.setString(3, getSexo());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alterar() {
		String sql = "update paciente set nome=?, data_nasc=?, sexo=? where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setString(1, getNome());
			ps.setDate(2, new java.sql.Date(getNascimento().getTime()));
			ps.setString(3, getSexo());
			ps.setInt(4, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void excluir() {
		String sql = "delete from paciente where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Cliente> listar(String filtro) {
		ArrayList<Cliente> lista = new ArrayList<Cliente>();
		String sql = "select * from paciente order by nome";
		//if(filtro != null) {
		//	sql = "select * from paciente where nome like ? order by nome";
		//}
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			
			if(filtro != null) {
				ps.setString(1, filtro);
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Cliente c = new Cliente();
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setNascimento(rs.getDate("data_nasc"));
				c.setSexo(rs.getString("sexo"));
				lista.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Cliente buscaPorID(int id) {
		Cliente c = new Cliente();
		String sql = "select * from paciente where id=?";
		try {
			PreparedStatement ps = Principal.conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setNascimento(rs.getDate("data_nasc"));
				c.setSexo(rs.getString("sexo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String[] toArray() {
		return new String[]{getId() + "", getNome(), getNascimento().toString(), getSexo()};
	}

	
}

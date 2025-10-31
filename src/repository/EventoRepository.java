package repository;

import jdk.jfr.Event;
import model.Evento;
import java.sql.*;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//achar todos os eventos no banco de dados
public class EventoRepository {
    public static List<Evento> buscarEventos(){
        List<Evento> todosEventos = new ArrayList<>();
        String sql =  "select * from Evento";
        try(Connection conexao = MyJDBC.getConnection(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet resultSet = stmt.executeQuery()){
            while(resultSet.next()){
                var evento = new Evento();
                evento.setId(resultSet.getInt("Evento_id"));
                evento.setNome(resultSet.getString("nome"));
                evento.setDescricao(resultSet.getString("descricao"));
                evento.setDataInicio(LocalDate.from(resultSet.getTimestamp("dataInicio").toLocalDateTime()));
                evento.setDataFim(LocalDate.from(resultSet.getTimestamp("dataFim").toLocalDateTime()));
                evento.setLocal(resultSet.getString("local"));
                evento.setCategoria(resultSet.getString("categoria"));
                evento.setStatus(resultSet.getString("status"));
                todosEventos.add(evento);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return todosEventos;
    }
    public void salvar(Evento evento) {
        String sql = "INSERT INTO Evento (nome, descricao, dataInicio, dataFim, local, categoria, status) " +
                "VALUES ()";
        try (Connection conexao = MyJDBC.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            if (evento.getDataInicio() != null) {stmt.setDate(3, java.sql.Date.valueOf(evento.getDataInicio()));
            } else {stmt.setNull(3, java.sql.Types.DATE);}

            if (evento.getDataFim() != null) {stmt.setDate(4, java.sql.Date.valueOf(evento.getDataFim()));
            } else {stmt.setNull(4, java.sql.Types.DATE);}

            stmt.setString(5, evento.getLocal());
            stmt.setString(6, evento.getCategoria());
            stmt.setString(7, evento.getStatus());


            stmt.executeUpdate();
            System.out.println("Evento salvo com sucesso no Banco!");


        } catch (SQLException e) {
            System.err.println("Erro ao salvar evento: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean remover(int id) {
        String sql = "DELETE FROM Evento WHERE Evento_id = ?";


        try (Connection conexao = MyJDBC.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();


            if (linhasAfetadas > 0) {
                System.out.println("Evento removido com sucesso!");
                return true;
            } else {
                System.out.println("Nenhum evento encontrado com o ID informado.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao remover evento: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public List<Evento> listarTodos() {
        return buscarEventos();
    }

    public List<Evento> buscarPorData(java.sql.Date data) {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento WHERE DATE(dataInicio) = ?";

        try (Connection conn = MyJDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, data);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Evento e = new Evento();
                e.setId(rs.getInt("Evento_id"));
                e.setNome(rs.getString("nome"));
                e.setDescricao(rs.getString("descricao"));
                e.setDataInicio(rs.getTimestamp("dataInicio").toLocalDateTime().toLocalDate());
                e.setDataFim(rs.getTimestamp("dataFim").toLocalDateTime().toLocalDate());
                e.setLocal(rs.getString("local"));
                e.setCategoria(rs.getString("categoria"));
                e.setStatus(rs.getString("status"));
                eventos.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return eventos;
    }

}

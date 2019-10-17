package com.danilonogueira.veiculos.bean;

import com.danilonogueira.veiculos.dao.CarroDAO;
import com.danilonogueira.veiculos.entity.Carro;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CarroBean {

    private static final long serialVersionUID = 1L;

    private CarroDAO carroDAO;
    private Carro carro;
    private List<Carro> carros;

    private String estadoTela = "buscar";

    public CarroBean() {
        this.carroDAO = new CarroDAO();
        this.listar();
    }

    //CRIAR UM NOVO CARRO
    public Carro criarCarro() {
        this.carro = new Carro();
        return carro;
    }
    
    //BOTÃO NOVO
    public void novo() {
        carro = criarCarro();
        mudarInseri();
    }

    //CONFIGURAÇÃO DE MENSAGENS
    public void adicionarMensagem(String mensagem, FacesMessage.Severity tipoErro) {
        FacesMessage fm = new FacesMessage(tipoErro, mensagem, "");
        FacesContext.getCurrentInstance().addMessage(null, fm);
    }

    //VALIDA CAMPOS DE CARRO
    public boolean validaCarro(Carro carro) {
        return !(carro.getModelo().length() == 0 || carro.getFabricante().length() == 0 || carro.getCor().length() == 0 || carro.getAno() == 0);
    }

    //SALVAR UM CARRO
    public void salvar() {
        if (this.validaCarro(this.carro)) {
            if (this.carroDAO.incluir(this.carro)) {
                adicionarMensagem(carro.getModelo()+" "+carro.getFabricante()+" salvo com sucesso!", FacesMessage.SEVERITY_INFO);
                mudarBusca();
            } else {
                adicionarMensagem("Não foi possível incluir o veículo, tente novamente!", FacesMessage.SEVERITY_ERROR);
            }
        } else {
            adicionarMensagem("Os dados estão incompletos, verifique se todas informações foram preenchidas!", FacesMessage.SEVERITY_FATAL);
        }
    }

    //DELETAR UM CARRO
    public void deletar(Carro carro) {
        if (this.carroDAO.excluir(carro)) {
            carros.remove(carro);
            adicionarMensagem(carro.getModelo()+" "+carro.getFabricante()+" excluído com sucesso!", FacesMessage.SEVERITY_INFO);
        } else {
            adicionarMensagem("Não foi possível excluir o veículo, tente novamente!", FacesMessage.SEVERITY_ERROR);
        }
    }

    //EDITAR UM CARRO
    public void editar(Carro carro) {
        this.carro = carro;
        mudarEdita();
    }

    //LISTAR OS CARROS
    public void listar() {
        if (!isBusca()) {
            mudarBusca();
            return;
        }
        try {
            carros = this.carroDAO.listar();
            if (carros == null || carros.size() < 1) {
                adicionarMensagem("Não existe nenhum dado cadastrado", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception ex) {
            adicionarMensagem(ex.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    
    //METODOS PARA CONTROLE DE TELA
    public boolean isInseri() {
        return "inserir".equals(estadoTela);
    }

    public boolean isEdita() {
        return "editar".equals(estadoTela);
    }

    public boolean isBusca() {
        return "buscar".equals(estadoTela);
    }

    public void mudarInseri() {
        estadoTela = "inserir";
    }

    public void mudarEdita() {
        estadoTela = "editar";
    }

    public void mudarBusca() {
        estadoTela = "buscar";
        this.listar();
    }

    //METODOS GETTERS E SETTERS
    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }

    public String getEstadoTela() {
        return estadoTela;
    }

    public void setEstadoTela(String estadoTela) {
        this.estadoTela = estadoTela;
    }
    
    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public CarroDAO getCarroDAO() {
        return carroDAO;
    }

    public void setCarroDAO(CarroDAO carroDAO) {
        this.carroDAO = carroDAO;
    }


}

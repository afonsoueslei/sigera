package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.controle.tela.RequerimentoTelaConsulta;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class RequerimentoTelaConsultaDataModel extends ListDataModel<RequerimentoTelaConsulta> implements SelectableDataModel<RequerimentoTelaConsulta> {

    public RequerimentoTelaConsultaDataModel(List<RequerimentoTelaConsulta> list) {
        super(list);
    }

    public RequerimentoTelaConsultaDataModel() {
    }

    @Override
    public Object getRowKey(RequerimentoTelaConsulta r) {
        return r.getNumero();
    }

    @Override
    public RequerimentoTelaConsulta getRowData(String numero) {
        List<RequerimentoTelaConsulta> requerimentos = (List<RequerimentoTelaConsulta>) getWrappedData();

        for (RequerimentoTelaConsulta requerimento : requerimentos) {
            if (requerimento.getNumero() == Integer.parseInt(numero)) {
                return requerimento;
            }
        }

        return null;
    }
}

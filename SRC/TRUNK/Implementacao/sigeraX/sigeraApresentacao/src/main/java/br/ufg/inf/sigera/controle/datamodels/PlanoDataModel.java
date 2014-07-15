package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.controle.tela.PlanoTela;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class PlanoDataModel extends ListDataModel<PlanoTela> implements SelectableDataModel<PlanoTela> {

    public PlanoDataModel(List<PlanoTela> list) {
        super(list);
    }

    public PlanoDataModel() {
    }

    @Override
    public Object getRowKey(PlanoTela t) {
        return t.getNumero();
    }

    @Override
    public PlanoTela getRowData(String numero) {
        List<PlanoTela> planos = (List<PlanoTela>) getWrappedData();
        for (PlanoTela plano : planos) {
            if (plano.getNumero()== Integer.parseInt(numero)) {
                return plano;
            }
        }
        return null;
    }
}

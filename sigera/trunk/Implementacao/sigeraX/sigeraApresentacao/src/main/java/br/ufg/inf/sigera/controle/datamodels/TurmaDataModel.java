package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.controle.tela.TurmaTela;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class TurmaDataModel extends ListDataModel<TurmaTela> implements SelectableDataModel<TurmaTela> {

    public TurmaDataModel(List<TurmaTela> list) {
        super(list);
    }

    public TurmaDataModel() {
    }

    @Override
    public Object getRowKey(TurmaTela t) {
        return t.getNumero();
    }

    @Override
    public TurmaTela getRowData(String numero) {
        List<TurmaTela> turmas = (List<TurmaTela>) getWrappedData();
        for (TurmaTela turma : turmas) {
            if (turma.getNumero()== Integer.parseInt(numero)) {
                return turma;
            }
        }
        return null;
    }
}

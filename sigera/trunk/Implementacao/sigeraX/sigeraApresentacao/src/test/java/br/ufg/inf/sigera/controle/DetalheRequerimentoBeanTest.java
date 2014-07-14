/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.sigera.controle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author auf
 */
public class DetalheRequerimentoBeanTest {
    
    public DetalheRequerimentoBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of init method, of class DetalheRequerimentoBean.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
        instance.init();
    }

    /**
     * Test of getTurmasComStatus method, of class DetalheRequerimentoBean.
     */
//    @Test
//    public void testGetTurmasComStatus() {
//        System.out.println("getTurmasComStatus");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        List expResult = null;
//        List result = instance.getTurmasComStatus();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of setTurmasComStatus method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testSetTurmasComStatus() {
//        System.out.println("setTurmasComStatus");
//        List<TurmaComStatus> turmasComStatus = null;
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.setTurmasComStatus(turmasComStatus);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getJustificativaParecerAcerto method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetJustificativaParecerAcerto() {
//        System.out.println("getJustificativaParecerAcerto");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.getJustificativaParecerAcerto();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setJustificativaParecerAcerto method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testSetJustificativaParecerAcerto() {
//        System.out.println("setJustificativaParecerAcerto");
//        String justificativaParecerAcerto = "";
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.setJustificativaParecerAcerto(justificativaParecerAcerto);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isDocumentosConferidos method, of class DetalheRequerimentoBean.
//     */
    @Test
    public void testIsDocumentosConferidos() {
        System.out.println("isDocumentosConferidos");
        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
        boolean expResult = false;
        boolean result = instance.isDocumentosConferidos();
        assertEquals(expResult, result);
    }

//    /**
//     * Test of setDocumentosConferidos method, of class DetalheRequerimentoBean.
//     */
    @Test
    public void testSetDocumentosConferidos() {
        System.out.println("setDocumentosConferidos");
        boolean documentosConferidos = false;
        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
        instance.setDocumentosConferidos(documentosConferidos);
    }

//    /**
//     * Test of isCoordenadorEstagio method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testIsCoordenadorEstagio() {
//        System.out.println("isCoordenadorEstagio");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        boolean expResult = false;
//        boolean result = instance.isCoordenadorEstagio();
//        assertEquals(expResult, result);
//    }

//    /**
//     * Test of setCoordenadorEstagio method, of class DetalheRequerimentoBean.
//     */
    @Test
    public void testSetCoordenadorEstagio() {
        System.out.println("setCoordenadorEstagio");
        boolean coordenadorEstagio = false;
        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
        instance.setCoordenadorEstagio(coordenadorEstagio);
    }

//    /**
//     * Test of isDiretor method, of class DetalheRequerimentoBean.
//     */
    @Test
    public void testIsDiretor() {
        System.out.println("isDiretor");
        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
        boolean expResult = false;
        boolean result = instance.isDiretor();
        assertEquals(expResult, result);
    }

//    /**
//     * Test of setDiretor method, of class DetalheRequerimentoBean.
//     */
    @Test
    public void testSetDiretor() {
        System.out.println("setDiretor");
        boolean diretor = false;
        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
        instance.setDiretor(diretor);
    }

//    /**
//     * Test of getJustificativaConferencia method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetJustificativaConferencia() {
//        System.out.println("getJustificativaConferencia");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.getJustificativaConferencia();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setJustificativaConferencia method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testSetJustificativaConferencia() {
//        System.out.println("setJustificativaConferencia");
//        String justificativaConferencia = "";
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.setJustificativaConferencia(justificativaConferencia);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMensagemConfirmacao method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetMensagemConfirmacao() {
//        System.out.println("getMensagemConfirmacao");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.getMensagemConfirmacao();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getJustificativaDeferimento method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetJustificativaDeferimento() {
//        System.out.println("getJustificativaDeferimento");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.getJustificativaDeferimento();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setJustificativaDeferimento method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testSetJustificativaDeferimento() {
//        System.out.println("setJustificativaDeferimento");
//        String justificativaDeferimento = "";
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.setJustificativaDeferimento(justificativaDeferimento);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getListaStatus method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetListaStatus() {
//        System.out.println("getListaStatus");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        List expResult = null;
//        List result = instance.getListaStatus();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setListaStatus method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testSetListaStatus() {
//        System.out.println("setListaStatus");
//        List<EnumStatusRequerimento> listaStatus = null;
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.setListaStatus(listaStatus);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getStatusParecer method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetStatusParecer() {
//        System.out.println("getStatusParecer");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        int expResult = 0;
//        int result = instance.getStatusParecer();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setStatusParecer method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testSetStatusParecer() {
//        System.out.println("setStatusParecer");
//        int statusParecer = 0;
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.setStatusParecer(statusParecer);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getRequerimento method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetRequerimento() {
//        System.out.println("getRequerimento");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        Requerimento expResult = null;
//        Requerimento result = instance.getRequerimento();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getLoginBean method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetLoginBean() {
//        System.out.println("getLoginBean");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        LoginBean expResult = null;
//        LoginBean result = instance.getLoginBean();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setLoginBean method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testSetLoginBean() {
//        System.out.println("setLoginBean");
//        LoginBean loginBean = null;
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.setLoginBean(loginBean);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getTurmas method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetTurmas() {
//        System.out.println("getTurmas");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        Collection expResult = null;
//        Collection result = instance.getTurmas();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of cancelar method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testCancelar() {
//        System.out.println("cancelar");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.cancelar();
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of voltar method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testVoltar() {
//        System.out.println("voltar");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.voltar();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of voltarLista method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testVoltarLista() {
//        System.out.println("voltarLista");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.voltarLista();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of usuarioPodeCancelar method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testUsuarioPodeCancelar() {
//        System.out.println("usuarioPodeCancelar");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        boolean expResult = false;
//        boolean result = instance.usuarioPodeCancelar();
//        assertEquals(expResult, result);
//    }

//    /**
//     * Test of getDataProva method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetDataProva() {
//        System.out.println("getDataProva");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        Date expResult = null;
//        Date result = instance.getDataProva();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getTurma method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testGetTurma() {
//        System.out.println("getTurma");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        Turma expResult = null;
//        Turma result = instance.getTurma();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of podeVisualizarAnexos method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testPodeVisualizarAnexos() {
//        System.out.println("podeVisualizarAnexos");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        boolean expResult = false;
//        boolean result = instance.podeVisualizarAnexos();
//        assertEquals(expResult, result);
//    }

//    /**
//     * Test of podeVisualizarPareceres method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testPodeVisualizarPareceres() {
//        System.out.println("podeVisualizarPareceres");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        boolean expResult = false;
//        boolean result = instance.podeVisualizarPareceres();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of podeVisualizarTelefone method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testPodeVisualizarTelefone() {
//        System.out.println("podeVisualizarTelefone");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        boolean expResult = false;
//        boolean result = instance.podeVisualizarTelefone();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of usuarioPodeDarParecer method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testUsuarioPodeDarParecer() {
//        System.out.println("usuarioPodeDarParecer");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        boolean expResult = false;
//        boolean result = instance.usuarioPodeDarParecer();
//        assertEquals(expResult, result);
//    }

//    /**
//     * Test of usuarioPodeDarParecerAcertoMatricula method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testUsuarioPodeDarParecerAcertoMatricula() {
//        System.out.println("usuarioPodeDarParecerAcertoMatricula");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        boolean expResult = false;
//        boolean result = instance.usuarioPodeDarParecerAcertoMatricula();
//        assertEquals(expResult, result);
//    }

//    /**
//     * Test of cancelarParecer method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testCancelarParecer() {
//        System.out.println("cancelarParecer");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.cancelarParecer();
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of confirmarParecer method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testConfirmarParecer() {
//        System.out.println("confirmarParecer");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.confirmarParecer();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of obtenhaPareceres method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testObtenhaPareceres() {
//        System.out.println("obtenhaPareceres");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        Collection expResult = null;
//        Collection result = instance.obtenhaPareceres();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of cancelarConferencia method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testCancelarConferencia() {
//        System.out.println("cancelarConferencia");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.cancelarConferencia();
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of usuarioPodeConferirDocumentos method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testUsuarioPodeConferirDocumentos() {
//        System.out.println("usuarioPodeConferirDocumentos");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        boolean expResult = false;
//        boolean result = instance.usuarioPodeConferirDocumentos();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of validarConferencia method, of class DetalheRequerimentoBean.
//     */
    @Test
    public void testValidarConferencia() {
        System.out.println("validarConferencia");
        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
        boolean expResult = false;
        boolean result = instance.validarConferencia();
        assertEquals(expResult, result);
    }

//    /**
//     * Test of validarNotificacao method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testValidarNotificacao() {
//        System.out.println("validarNotificacao");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        boolean expResult = false;
//        boolean result = instance.validarNotificacao();
//        assertEquals(expResult, result);     
//    }
//
//    /**
//     * Test of confirmarConferencia method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testConfirmarConferencia() {
//        System.out.println("confirmarConferencia");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.confirmarConferencia();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of validarStatusParecerAcerto method, of class DetalheRequerimentoBean.
//     */
    @Test
    public void testValidarStatusParecerAcerto() {
        System.out.println("validarStatusParecerAcerto");
        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
        boolean expResult = false;
        boolean result = instance.validarStatusParecerAcerto();
        assertEquals(expResult, result);   
    }
//
//    /**
//     * Test of confirmarParecerAcerto method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testConfirmarParecerAcerto() {
//        System.out.println("confirmarParecerAcerto");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.confirmarParecerAcerto();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of cancelarParecerAcerto method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testCancelarParecerAcerto() {
//        System.out.println("cancelarParecerAcerto");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        instance.cancelarParecerAcerto();
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of visualizarEmenta method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testVisualizarEmenta() {
//        System.out.println("visualizarEmenta");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.visualizarEmenta();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of cssStatus method, of class DetalheRequerimentoBean.
//     */
//    @Test
//    public void testCssStatus() {
//        System.out.println("cssStatus");
//        DetalheRequerimentoBean instance = new DetalheRequerimentoBean();
//        String expResult = "";
//        String result = instance.cssStatus();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }
}
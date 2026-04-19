package Ejercicios.Trivial;

public class Preguntas {
    private String enunciado;
    private String respuestaA;
    private String respuestaB;
    private String respuestaC;
    private String respuestaD;
    private String respuestaCorrecta;

    public Preguntas(String enunciado, String respuestaA, String respuestaB, String respuestaC, String respuestaD, String respuestaCorrecta) {
        this.enunciado = enunciado;
        this.respuestaA = respuestaA;
        this.respuestaB = respuestaB;
        this.respuestaC = respuestaC;
        this.respuestaD = respuestaD;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    @Override
    public String toString() {
        return "Preguntas{" +
                "enunciado='" + enunciado + '\'' +
                ", respuestaA='" + respuestaA + '\'' +
                ", respuestaB='" + respuestaB + '\'' +
                ", respuestaC='" + respuestaC + '\'' +
                ", respuestaD='" + respuestaD + '\'' +
                ", respuestaCorrecta='" + respuestaCorrecta + '\'' +
                '}';
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespuestaA() {
        return respuestaA;
    }

    public void setRespuestaA(String respuestaA) {
        this.respuestaA = respuestaA;
    }

    public String getRespuestaB() {
        return respuestaB;
    }

    public void setRespuestaB(String respuestaB) {
        this.respuestaB = respuestaB;
    }

    public String getRespuestaC() {
        return respuestaC;
    }

    public void setRespuestaC(String respuestaC) {
        this.respuestaC = respuestaC;
    }

    public String getRespuestaD() {
        return respuestaD;
    }

    public void setRespuestaD(String respuestaD) {
        this.respuestaD = respuestaD;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }


}

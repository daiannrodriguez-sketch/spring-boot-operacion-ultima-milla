package co.edu.sena.operacionultimamilla.model;

public class ResumenPedidosDTO {

    private long total;
    private long pendientes;
    private long confirmados;
    private long despachados;
    private long cancelados;
    private long urgentes;

    public ResumenPedidosDTO() {
    }

    public ResumenPedidosDTO(long total, long pendientes, long confirmados, long despachados, long cancelados, long urgentes) {
        this.total = total;
        this.pendientes = pendientes;
        this.confirmados = confirmados;
        this.despachados = despachados;
        this.cancelados = cancelados;
        this.urgentes = urgentes;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPendientes() {
        return pendientes;
    }

    public void setPendientes(long pendientes) {
        this.pendientes = pendientes;
    }

    public long getConfirmados() {
        return confirmados;
    }

    public void setConfirmados(long confirmados) {
        this.confirmados = confirmados;
    }

    public long getDespachados() {
        return despachados;
    }

    public void setDespachados(long despachados) {
        this.despachados = despachados;
    }

    public long getCancelados() {
        return cancelados;
    }

    public void setCancelados(long cancelados) {
        this.cancelados = cancelados;
    }

    public long getUrgentes() {
        return urgentes;
    }

    public void setUrgentes(long urgentes) {
        this.urgentes = urgentes;
    }
}
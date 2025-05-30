// Enumeração que representa os possíveis status de uma carga no sistema de transportadora.

package com.mycompany.sistema_transportadora.model.enums;

public enum StatusCarga {
    PENDENTE, // Indica que a carga foi registrada, mas ainda não está pronta para transporte.
    ARMAZENADA, // Indica que a carga está armazenada em algum centro de distribuição ou depósito.
    EM_TRANSPORTE, // Indica que a carga está em deslocamento, ou seja, em trânsito até o destino.
    ENTREGUE, // Indica que a carga chegou corretamente ao destinatário final.
    EXTRAVIADA, // Indica que a carga foi extraviada, ou seja, perdida ou não localizada.
    CANCELADA; // Indica que a operação de transporte da carga foi cancelada.
 
    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase(); //  Retorna uma representação legível do nome do status.
    }
}

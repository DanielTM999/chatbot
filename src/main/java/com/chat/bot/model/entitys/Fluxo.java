package com.chat.bot.model.entitys;


import java.util.Map;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fluxo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String pergunta;

    @ElementCollection
    @CollectionTable(name = "fluxo_resposta", joinColumns = @JoinColumn(name = "fluxo_id"))
    @MapKeyColumn(name = "ordem")
    @Column(name = "resposta", nullable = true)
    private Map<Integer, Fluxo> resposta;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = true)
    private Usuarios usuario;
    
    @Override
    public String toString() {
        return "Fluxo [id=" + id + ", pergunta=" + pergunta + ", resposta=" + resposta + ", usuario="+ usuario.getCnpj()+"]";
    }
    
    @Data
    public static class Builder{
        private Long id;
        private String pergunta;
        private Map<Integer, Fluxo> resposta;
        private Usuarios usuario;

        public Builder pergunta(Long id) {
            this.id = id;
            return this;
        }

        public Builder pergunta(String pergunta) {
            this.pergunta = pergunta;
            return this;
        }

        public Builder resposta(Map<Integer, Fluxo> resposta) {
            this.resposta = resposta;
            return this;
        }

        public Builder usuario(Usuarios usuario) {
            this.usuario = usuario;
            return this;
        }

        public Fluxo build() {
            return new Fluxo(id, pergunta, resposta, usuario);
        }
    }

}

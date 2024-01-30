package com.jonasestevam.streaming.enuns;

public enum CategoriesEnum {

    EDUCACAO("Educação", "Vídeos educativos, tutoriais, aulas"),
    ENTRETENIMENTO("Entretenimento", "Conteúdo geral de entretenimento, como comédia, esquetes"),
    FILMESSERIES("Filmes e Series", "Trailers, resenhas, discussões sobre filmes e séries"),
    MUSICA("Música", "Videoclipes, apresentações ao vivo, documentários sobre música"),
    ESPORTES("Esportes", "Destaques de jogos, análises esportivas, entrevistas com atletas"),
    GAMING("Gaming", "Gameplays, análises de jogos, notícias sobre videogames"),
    TECNOLOGIA("Tecnologia", "Novidades em tecnologia, análises de produtos, tutoriais"),
    VIAGENS("Viagens", "Vlogs de viagem, guias turísticos, experiências culturais"),
    CULINARIA("Culinária", "Receitas, técnicas de culinária, críticas de restaurantes"),
    SAUDEBEMESTAR("Sapude e Bem-Estar", "Exercícios, dicas de saúde, meditação e mindfulness"),
    DIYARTESANATO("DIY e Artesanatos", "Projetos faça-você-mesmo, artesanato, técnicas artísticas"),
    BELEZAMODA("Beleza e Moda", "Tutoriais de maquiagem, dicas de moda, resenhas de produtos"),
    NOTICIASPOLITICA("Notícias e Política", "Cobertura de notícias, análises políticas, documentários"),
    CIENCIATECNOLOGIA("Ciência e Tecnologia", "Documentários científicos, explicações, novidades tecnológicas"),
    NEGOCIOSFINANCAS("Negocios e Finanças",
            "Conselhos sobre investimentos, notícias do mercado, estratégias de negócios"),
    AUTOMOTIVO("Automotivo", "Resenhas de carros, novidades do setor automotivo, tutoriais de manutenção"),
    ANIMAISNATUREZA("Animais e Natureza",
            "Vídeos de vida selvagem, documentários sobre natureza, cuidados com animais"),
    HISTORIACULTURA("História e Cultura", "Documentários históricos, análises culturais, biografias"),
    VLOGSPESSOAIS("Vlogs Pessoais", "Diários em vídeo, rotinas diárias, desafios"),
    INFANTIL("Infantil", "Desenhos animados, conteúdo educativo para crianças, histórias"),
    OUTRO("Outro", "Outro");

    private final String name;
    private final String description;

    CategoriesEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

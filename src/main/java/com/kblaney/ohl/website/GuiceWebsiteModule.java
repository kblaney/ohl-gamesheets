package com.kblaney.ohl.website;

import org.w3c.dom.Document;
import com.google.common.base.Function;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.kblaney.commons.io.UrlContentsGetter;
import com.kblaney.commons.io.UsAsciiUrlContentsGetter;
import com.kblaney.commons.xml.JtidyUrlToDomDocumentFunction;
import com.kblaney.commons.xml.UrlToDomDocumentFunction;
import com.kblaney.ohl.GoalieStats;
import com.kblaney.ohl.PlayerBio;
import com.kblaney.ohl.PlayerStats;
import com.kblaney.ohl.PlayerType;
import java.util.Set;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class GuiceWebsiteModule extends AbstractModule
{
  @Override
  protected void configure()
  {
    bind(UrlContentsGetter.class).to(UsAsciiUrlContentsGetter.class);
    bind(new TypeLiteral<Function<String, Set<NumberedTeam>>>() {}).
          to(PlayerStatsHtmlToTeamsFunction.class);
    bind(PlayerSupplier.class).to(PlayerSupplierImpl.class);
    bind(GoalieSupplier.class).to(GoalieSupplierImpl.class);
    bind(new TypeLiteral<Function<Node, GoalieStats>>() {}).
          to(GoalieTableRowNodeToStatsFunction.class);
    bind(new TypeLiteral<Function<Node, PlayerType>>() {}).
          to(PlayerTableRowNodeToPlayerTypeFunction.class);
    bind(new TypeLiteral<Function<Node, Integer>>() {}).
          to(PlayerTableRowNodeToSweaterNumFunction.class);
    bind(new TypeLiteral<Function<Node, PlayerStats>>() {}).
          to(PlayerTableRowNodeToStatsFunction.class);
    bind(new TypeLiteral<Function<String, PlayerBio>>() {}).
          to(PlayerIdToBioFunction.class);
    bind(new TypeLiteral<Function<String, Document>>() {}).
          to(PlayerIdToBioDocumentFunction.class);
    bind(new TypeLiteral<Function<Document, Node>>() {}).
          to(PlayerBioDocumentToBioDivNodeFunction.class);
    bind(new TypeLiteral<Function<Document, String>>() {}).
          to(PlayerBioDocumentToGameByGameFilePath.class);
    bind(PlayerStreaksSupplier.class).to(PlayerStreaksSupplierImpl.class);
    bind(new TypeLiteral<Function<String, NodeList>>() {}).
          to(PlayerGameByGameFilePathToGameByGameRowNodeListFunction.class);
    bind(TeamNumToNodeListFunction.class).annotatedWith(Names.named("Players")).
          to(ToPlayerTableRowNodeListFunction.class);
    bind(TeamNumToNodeListFunction.class).annotatedWith(Names.named("Goalies")).
          to(ToGoalieTableRowNodeListFunction.class);
    bind(new TypeLiteral<Function<Node, String>>() {}).
          annotatedWith(Names.named("ToNameFunction")).
          to(PlayerTableRowNodeToNameFunction.class);
    bind(new TypeLiteral<Function<Node, String>>() {}).
          annotatedWith(Names.named("ToIdFunction")).
          to(PlayerTableRowNodeToIdFunction.class);
  }

  @Provides
  @SuppressWarnings("unused")
  private UrlToDomDocumentFunction provideUrlToDomDocumentFunction()
  {
    final int maxNumAttempts = 10;
    return new JtidyUrlToDomDocumentFunction(maxNumAttempts);
  }
}

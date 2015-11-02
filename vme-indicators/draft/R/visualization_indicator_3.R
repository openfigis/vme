# Visualization experiments for indicator 3

library(ggplot2)
library(RColorBrewer)
library(httr)

req <- GET("http://figisapps.fao.org/figis/geoserver.dv.2/vme/ows?service=WFS&version=1.0.0&request=GetFeature&typeName=vme:VME_INDICATOR_3&outputFormat=CSV")
data<- content(req)

ggplot(data, aes(x=YEAR, y=COUNT, fill=OWNER, order=OWNER)) + 
      geom_bar(stat="identity", width=0.8) + 
      geom_bar(stat="identity", width=0.8, colour="grey20", show_guide=FALSE) + 
      scale_fill_manual(name="Owner", values=brewer.pal(length(levels(as.factor(data$OWNER))), "Set1")) +
      xlab("Year") + ylab("Number of VME closures") + 
      ggtitle("Number of VME closures by owner and year") +
      theme(legend.position="bottom")

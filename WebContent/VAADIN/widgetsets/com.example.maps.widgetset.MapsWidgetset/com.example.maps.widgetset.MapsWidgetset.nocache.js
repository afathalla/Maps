function com_example_maps_widgetset_MapsWidgetset(){var l='',F='" for "gwt:onLoadErrorFn"',D='" for "gwt:onPropertyErrorFn"',n='"><\/script>',p='#',r='/',kb='<script defer="defer">com_example_maps_widgetset_MapsWidgetset.onInjectionDone(\'com.example.maps.widgetset.MapsWidgetset\')<\/script>',qb='<script id="',A='=',q='?',C='Bad handler "',jb='DOMContentLoaded',ib='E495479017BBBC8F8DD9E1E1F8ADA11C.cache.html',o='SCRIPT',pb='__gwt_marker_com.example.maps.widgetset.MapsWidgetset',s='base',lb='begin',cb='bootstrap',u='clear.cache.gif',m='com.example.maps.widgetset.MapsWidgetset',z='content',ob='end',mb='gwt.hybrid',E='gwt:onLoadErrorFn',B='gwt:onPropertyErrorFn',y='gwt:property',gb='hosted.html?com_example_maps_widgetset_MapsWidgetset',ab='iframe',t='img',bb="javascript:''",fb='loadExternalRefs',v='meta',eb='moduleRequested',nb='moduleStartup',w='name',db='position:absolute;width:0;height:0;border:none',hb='selectingPermutation',x='startup';var sb=window,k=document,rb=sb.__gwtStatsEvent?function(a){return sb.__gwtStatsEvent(a)}:null,gc,Cb,xb,wb=l,Fb={},jc=[],fc=[],vb=[],cc,ec;rb&&rb({moduleName:m,subSystem:x,evtGroup:cb,millis:(new Date()).getTime(),type:lb});if(!sb.__gwt_stylesLoaded){sb.__gwt_stylesLoaded={}}if(!sb.__gwt_scriptsLoaded){sb.__gwt_scriptsLoaded={}}function Bb(){var b=false;try{b=sb.external&&(sb.external.gwtOnLoad&&sb.location.search.indexOf(mb)==-1)}catch(a){}Bb=function(){return b};return b}
function Eb(){if(gc&&Cb){var c=k.getElementById(m);var b=c.contentWindow;if(Bb()){b.__gwt_getProperty=function(a){return yb(a)}}com_example_maps_widgetset_MapsWidgetset=null;b.gwtOnLoad(cc,m,wb);rb&&rb({moduleName:m,subSystem:x,evtGroup:nb,millis:(new Date()).getTime(),type:ob})}}
function zb(){var j,h=pb,i;k.write(qb+h+n);i=k.getElementById(h);j=i&&i.previousSibling;while(j&&j.tagName!=o){j=j.previousSibling}function f(b){var a=b.lastIndexOf(p);if(a==-1){a=b.length}var c=b.indexOf(q);if(c==-1){c=b.length}var d=b.lastIndexOf(r,Math.min(c,a));return d>=0?b.substring(0,d+1):l}
;if(j&&j.src){wb=f(j.src)}if(wb==l){var e=k.getElementsByTagName(s);if(e.length>0){wb=e[e.length-1].href}else{wb=f(k.location.href)}}else if(wb.match(/^\w+:\/\//)){}else{var g=k.createElement(t);g.src=wb+u;wb=f(g.src)}if(i){i.parentNode.removeChild(i)}}
function dc(){var f=document.getElementsByTagName(v);for(var d=0,g=f.length;d<g;++d){var e=f[d],h=e.getAttribute(w),b;if(h){if(h==y){b=e.getAttribute(z);if(b){var i,c=b.indexOf(A);if(c>=0){h=b.substring(0,c);i=b.substring(c+1)}else{h=b;i=l}Fb[h]=i}}else if(h==B){b=e.getAttribute(z);if(b){try{ec=eval(b)}catch(a){alert(C+b+D)}}}else if(h==E){b=e.getAttribute(z);if(b){try{cc=eval(b)}catch(a){alert(C+b+F)}}}}}}
function yb(d){var e=fc[d](),b=jc[d];if(e in b){return e}var a=[];for(var c in b){a[b[c]]=c}if(ec){ec(d,a,e)}throw null}
var Ab;function Db(){if(!Ab){Ab=true;var a=k.createElement(ab);a.src=bb;a.id=m;a.style.cssText=db;a.tabIndex=-1;k.body.appendChild(a);rb&&rb({moduleName:m,subSystem:x,evtGroup:nb,millis:(new Date()).getTime(),type:eb});a.contentWindow.location.replace(wb+hc)}}
com_example_maps_widgetset_MapsWidgetset.onScriptLoad=function(){if(Ab){Cb=true;Eb()}};com_example_maps_widgetset_MapsWidgetset.onInjectionDone=function(){gc=true;rb&&rb({moduleName:m,subSystem:x,evtGroup:fb,millis:(new Date()).getTime(),type:ob});Eb()};zb();var hc;if(Bb()){if(sb.external.initModule&&sb.external.initModule(m)){sb.location.reload();return}hc=gb}dc();rb&&rb({moduleName:m,subSystem:x,evtGroup:cb,millis:(new Date()).getTime(),type:hb});if(!hc){try{hc=ib}catch(a){return}}var bc;function ac(){if(!xb){xb=true;Eb();if(k.removeEventListener){k.removeEventListener(jb,ac,false)}if(bc){clearInterval(bc)}}}
if(k.addEventListener){k.addEventListener(jb,function(){Db();ac()},false)}var bc=setInterval(function(){if(/loaded|complete/.test(k.readyState)){Db();ac()}},50);rb&&rb({moduleName:m,subSystem:x,evtGroup:cb,millis:(new Date()).getTime(),type:ob});rb&&rb({moduleName:m,subSystem:x,evtGroup:fb,millis:(new Date()).getTime(),type:lb});k.write(kb)}
com_example_maps_widgetset_MapsWidgetset();
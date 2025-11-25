package com.handmade.ecommerce.artisan.service.postSaveHooks;

import com.handmade.ecommerce.artisan.model.Artisan;
import org.chenile.workflow.model.TransientMap;
import org.chenile.workflow.service.stmcmds.PostSaveHook;

/**
 Contains customized post Save Hook for the State ID.
*/
public class INACTIVEArtisanPostSaveHook implements PostSaveHook<Artisan>{
	@Override
    public void execute(Artisan artisan, TransientMap map){
    }
}

/*
 * Copyright (c) 2007 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 * REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 * INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 *
 */

package com.sun.j3d.utils.scenegraph.io.state.javax.media.j3d;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TextureUnitState;

import com.sun.j3d.utils.scenegraph.io.retained.Controller;
import com.sun.j3d.utils.scenegraph.io.retained.SymbolTableData;

public class TextureUnitStateState extends NodeComponentState {

    private int texCoordGeneration;
    private int texture;
    private int textureAttributes;

    public TextureUnitStateState(SymbolTableData symbol,Controller control) {
        super(symbol, control);

        if (node!=null) {
            TextureUnitState attr = (TextureUnitState)node;
            texCoordGeneration = control.getSymbolTable().addReference( attr.getTexCoordGeneration() );
            texture = control.getSymbolTable().addReference( attr.getTexture() );
            textureAttributes =control.getSymbolTable().addReference( attr.getTextureAttributes() );
        }
    }

    @Override
    public void writeObject( DataOutput out ) throws IOException {
        super.writeObject( out );
        out.writeInt( texCoordGeneration );
        out.writeInt( texture );
        out.writeInt( textureAttributes );
    }

    @Override
    public void readObject( DataInput in ) throws IOException {
        super.readObject( in );
        TextureUnitState attr = (TextureUnitState)node;
        texCoordGeneration = in.readInt();
        texture = in.readInt();
        textureAttributes = in.readInt();
    }

    @Override
    public void addSubReference() {
        control.getSymbolTable().incNodeComponentRefCount( texCoordGeneration );
        control.getSymbolTable().incNodeComponentRefCount( texture );
        control.getSymbolTable().incNodeComponentRefCount( textureAttributes );
    }

    @Override
    public void buildGraph() {
        TextureUnitState attr = (TextureUnitState)node;
        attr.setTexCoordGeneration( (TexCoordGeneration)control.getSymbolTable().getJ3dNode( texCoordGeneration ));
        attr.setTexture( (Texture)control.getSymbolTable().getJ3dNode( texture ));
        attr.setTextureAttributes( (TextureAttributes)control.getSymbolTable().getJ3dNode( textureAttributes ));
        super.buildGraph();     // Must be last call in method
    }

    @Override
    protected javax.media.j3d.SceneGraphObject createNode() {
        return new TextureUnitState();
    }


}


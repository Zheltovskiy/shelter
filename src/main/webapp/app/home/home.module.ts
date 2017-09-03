import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShelterSharedModule } from '../shared';
import {MdCardModule} from '@angular/material';

import { HOME_ROUTE, HomeComponent } from './';
import {HomeVisitorComponent} from './home-visitor.component';

@NgModule({
    imports: [
        ShelterSharedModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true }),
        MdCardModule
    ],
    declarations: [
        HomeComponent,
        HomeVisitorComponent,

    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShelterHomeModule {}

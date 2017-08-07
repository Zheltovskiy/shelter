import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Pet } from './pet.model';
import { PetService } from './pet.service';

@Component({
    selector: 'jhi-pet-detail',
    templateUrl: './pet-detail.component.html'
})
export class PetDetailComponent implements OnInit, OnDestroy {

    pet: Pet;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private petService: PetService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPets();
    }

    load(id) {
        this.petService.find(id).subscribe((pet) => {
            this.pet = pet;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'petListModification',
            (response) => this.load(this.pet.id)
        );
    }
}

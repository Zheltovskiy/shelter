import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Shelter } from './shelter.model';
import { ShelterService } from './shelter.service';

@Component({
    selector: 'jhi-shelter-detail',
    templateUrl: './shelter-detail.component.html'
})
export class ShelterDetailComponent implements OnInit, OnDestroy {

    shelter: Shelter;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shelterService: ShelterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShelters();
    }

    load(id) {
        this.shelterService.find(id).subscribe((shelter) => {
            this.shelter = shelter;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShelters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shelterListModification',
            (response) => this.load(this.shelter.id)
        );
    }
}
